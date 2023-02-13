package rip.snicon.calculator.v2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindowController {
    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose;
    @FXML private Label lblResult;

    private double x, y;
    private double num1 = 0;
    private String operator = "+";

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        // setIconified => Hide/minimize the application.
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
    }

    @FXML
    void onNumberClicked(MouseEvent event) {
        // get the value of clicked number button by parsing the int from the id - the "btn" prefix.
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn", ""));
        // Update the lblResult label to display the number selected.
        lblResult.setText(Double.parseDouble(lblResult.getText())==0?String.valueOf((double)value):String.valueOf(Double.parseDouble(lblResult.getText())*10+value));
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        // Similar to the "value" variable in onNumberClicked(). -> Sense what symbol is clicked. -> run relevant code.
        String symbol = ((Pane)event.getSource()).getId().replace("btn", "");
        // DEBUG: System.out.println("Symbol is: " + symbol);
        if(symbol.equals("Equals")) {
            double num2 = Double.parseDouble(lblResult.getText());
            switch (operator) {
                case "+" -> lblResult.setText((num1+num2) + "");
                case "-" -> lblResult.setText((num1-num2) + "");
                case "*" -> lblResult.setText((num1*num2) + "");
                case "/" -> lblResult.setText((num1/num2) + "");
                case "^" -> lblResult.setText((Math.pow(num1, num2)) + "");
                case "√" -> lblResult.setText(String.valueOf(Math.round(Math.pow(num1, 1.0 / num2))));
                case "%" -> lblResult.setText(Math.round((num1/num2) * 100) + "%");
                // todo: Add support for decimals
            }
            operator = ".";
        } else if (symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            operator = ".";
        }
        else {
            switch (symbol) {
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
                case "PowerOf" -> operator = "^";
                case "RootOf" -> operator = "√";
                case "Percent" -> operator = "%";
                // todo: Add support for decimals
            }
            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText(String.valueOf(0.0));
        }
    }
}
