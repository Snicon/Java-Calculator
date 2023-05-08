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
    @FXML private Label lblPrevNum;

    private double x, y;
    private double num1 = 0;
    private String total = "";
    private String operator = "";
    private boolean check = true;

    public String create(double num1, double num2, String operator) {
        switch (operator) {
            case "+" -> {
                return String.valueOf(num1 + num2);
            }
            case "-" -> {
                return String.valueOf(num1 - num2);
            }
            case "*" -> {
                return String.valueOf(num1 * num2);
            }
            case "/" -> {
                if(num2 == 0)
                        return "0.0";
                return String.valueOf(num1 / num2);
            }
            case "^" -> {
                return String.valueOf(Math.pow(num1, num2));
            }
            case "√" -> {
                return String.valueOf(Math.round(Math.pow(num1, 1.0 / num2)));
            }
            case "%" -> {
                return (Math.round((num1/num2) * 100) + "%");
            }
            default -> {}
        }
        return "0.0";
    }

    public void number(String number) {
        lblResult.setText(lblResult.getText() + number);
    }

    public void prevNumber(String number) {
        lblPrevNum.setText(lblPrevNum.getText() + number);
    }

    public void prevOperator(String operator) {
        lblPrevNum.setText(lblPrevNum.getText() + " " + operator + " ");
    }

    public void computeProcess(MouseEvent event) {
        if(check) {
            lblResult.setText("");
            lblPrevNum.setText("");

            check = false;
        }

        // Get the number we want to compute
        String value = ((Pane)event.getSource()).getId().replace("btn", "");

        if(!value.equals("Decimal")) {
            number(value);
            prevNumber(value);
        } else {
            number(".");
            prevNumber(".");
        }

    }

    public void operatorProcess(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn", "");

        if(!symbol.equals("Equals") && !symbol.equals("Clear")) {
            if(!operator.isEmpty())
                return;
            switch (symbol) {
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
                case "PowerOf" -> operator = "^";
                case "RootOf" -> operator = "√";
                case "Percent" -> operator = "%";
            }
            prevOperator(operator);
            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText("");
        } else if(symbol.equals("Clear")) {
            lblResult.setText("0");
            lblPrevNum.setText("");
        } else {
            if(operator.isEmpty())
                return;

            double num2 = Double.parseDouble(lblResult.getText());
            total = create(num1, num2, operator);

            lblResult.setText(String.valueOf(total));

            operator = "";
            check = true;
        }
    }

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
}

