package com.kskrueger.college.gui.dragable;

import com.kskrueger.college.util.Course;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.EventListener;

public class DraggablePanelV1 extends Application {
    private final BooleanProperty dragModeActiveProperty = new SimpleBooleanProperty(
            this, "dragModeActive", true);
    private static final IntegerProperty totalCredits = new SimpleIntegerProperty();

    @Override
    public void start(final Stage stage) {
        final Node coursePanel = makeDraggable(createCoursePanel(),200,100);

        final Pane panelsPane = new Pane();
        panelsPane.getChildren().addAll(coursePanel);

        final BorderPane sceneRoot = new BorderPane();

        BorderPane.setAlignment(panelsPane, Pos.TOP_LEFT);
        sceneRoot.setCenter(panelsPane);

        final CheckBox dragModeCheckbox = new CheckBox("Drag mode");

        final Button addButton = new Button("Add Course");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Course added");
                Node newCourse = makeDraggable(createCoursePanel(), 200, 100);
                panelsPane.getChildren().add(newCourse);
            }
        });

        final Label creditsLabel = new Label("Total Credits: ");
        creditsLabel.textProperty().bind(totalCredits.asString());

        final HBox bottomBox = createHBox(6, dragModeCheckbox, addButton, creditsLabel);
        BorderPane.setMargin(bottomBox, new Insets(6));
        sceneRoot.setBottom(bottomBox);

        dragModeActiveProperty.bind(dragModeCheckbox.selectedProperty());

        final Scene scene = new Scene(sceneRoot, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Draggable Panels Example");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);

    }

    private Node makeDraggable(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                if (dragModeActiveProperty.get()) {
                    // disable mouse events for all children
                    mouseEvent.consume();
                }
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // remember initial mouse cursor coordinates
                            // and node position
                            dragContext.mouseAnchorX = mouseEvent.getX();
                            dragContext.mouseAnchorY = mouseEvent.getY();
                            dragContext.initialTranslateX = node.getTranslateX();
                            dragContext.initialTranslateY = node.getTranslateY();
                        }
                    }
                });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // shift node from its initial position by delta
                            // calculated from mouse cursor movement
                            node.setTranslateX(dragContext.initialTranslateX
                                    + mouseEvent.getX() - dragContext.mouseAnchorX);
                            node.setTranslateY(dragContext.initialTranslateY
                                    + mouseEvent.getY() - dragContext.mouseAnchorY);
                        }
                    }
                });

        return wrapGroup;
    }

    private Node makeDraggable(final Node node, int gridX, int gridY) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                if (dragModeActiveProperty.get()) {
                    // disable mouse events for all children
                    //mouseEvent.consume();
                }
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // remember initial mouse cursor coordinates
                            // and node position
                            dragContext.mouseAnchorX = ((int)(mouseEvent.getX())/gridX)*gridX;
                            dragContext.mouseAnchorY = ((int)(mouseEvent.getY())/gridY)*gridY;
                            dragContext.initialTranslateX = node.getTranslateX();
                            dragContext.initialTranslateY = node.getTranslateY();
                        }
                    }
                });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // shift node from its initial position by delta
                            // calculated from mouse cursor movement
                            node.setTranslateX(dragContext.initialTranslateX
                                    + ((int)(mouseEvent.getX())/gridX)*gridX - dragContext.mouseAnchorX);
                            node.setTranslateY(dragContext.initialTranslateY
                                    + ((int)(mouseEvent.getY())/gridY)*gridY - dragContext.mouseAnchorY);
                        }
                    }
                });

        return wrapGroup;
    }

    private static Node createCoursePanel() {
        final TextField courseInput = new TextField();
        final Label courseNameLabel = new Label("Name: ");
        final Label courseCreditsLabel = new Label("Credits: ");
        final PrevCourse prevCourse = new PrevCourse();

        courseInput.setPromptText("Enter a course number");
        courseInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Course course = new Course(courseInput.getText());
                courseNameLabel.setText(course.getRealName());
                courseCreditsLabel.setText(course.getCredits());
                System.out.println("Course updated");
                System.out.println(prevCourse.credits);
                int credits = Integer.parseInt(course.getCredits());
                prevCourse.credits = credits;
                int value = totalCredits.getValue();
                totalCredits.setValue(value-prevCourse.credits+credits);
            }
        });

        final VBox panel = createVBox(6, courseInput, courseNameLabel, courseCreditsLabel);
        panel.setAlignment(Pos.CENTER_LEFT);
        configureBorder(panel);

        return panel;
    }

    private static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;" + "-fx-border-color: black;"
                + "-fx-border-width: 1;" + "-fx-border-radius: 6;" + "-fx-padding: 6;");
    }

    private static HBox createHBox(final double spacing, final Node... children) {
        final HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(children);
        return hbox;
    }

    private static VBox createVBox(final double spacing, final Node... children) {
        final VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(children);
        return vbox;
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private static class PrevCourse {
        public double credits;
    }
}