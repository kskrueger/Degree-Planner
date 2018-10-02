package com.kskrueger.college.gui.dragable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxDragDropExample1 extends Application
{
    // Create the ListViews
    ListView<Fruit> sourceView = new ListView<>();
    ListView<Fruit> targetView = new ListView<>();

    // Create the LoggingArea
    TextArea loggingArea = new TextArea("");

    // Set the Custom Data Format
    static final DataFormat FRUIT_LIST = new DataFormat("FruitList");

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        // Create the Labels
        Label sourceListLbl = new Label("Source List: ");
        Label targetListLbl = new Label("Target List: ");
        Label messageLbl = new Label("Select one or more fruits from a list, drag and drop them to another list");

        // Set the Size of the Views and the LoggingArea
        sourceView.setPrefSize(200, 200);
        targetView.setPrefSize(200, 200);
        loggingArea.setMaxSize(410, 200);

        // Add the fruits to the Source List
        sourceView.getItems().addAll(this.getFruitList());

        // Allow multiple-selection in lists
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        targetView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create the GridPane
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        // Add the Labels and Views to the Pane
        pane.add(messageLbl, 0, 0, 3, 1);
        pane.addRow(1, sourceListLbl, targetListLbl);
        pane.addRow(2, sourceView, targetView);

        // Add mouse event handlers for the source
        sourceView.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Source: drag detected");
                dragDetected(event, sourceView);
            }
        });

        sourceView.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag over");
                dragOver(event, sourceView);
            }
        });

        sourceView.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag dropped");
                dragDropped(event, sourceView);
            }
        });

        sourceView.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag done");
                dragDone(event, sourceView);
            }
        });

        // Add mouse event handlers for the target
        targetView.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Target: drag detected");
                dragDetected(event, targetView);
            }
        });

        targetView.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag over");
                dragOver(event, targetView);
            }
        });

        targetView.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag dropped");
                dragDropped(event, targetView);
            }
        });

        targetView.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag done");
                dragDone(event, targetView);
            }
        });

        // Create the VBox
        VBox root = new VBox();
        // Add the Pane and The LoggingArea to the VBox
        root.getChildren().addAll(pane,loggingArea);
        // Set the Style of the VBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("A Drag and Drop Example for Custom Data Types");
        // Display the Stage
        stage.show();
    }

    // Create the Fruit List
    private ObservableList<Fruit> getFruitList()
    {
        ObservableList<Fruit> list = FXCollections.<Fruit>observableArrayList();

        Fruit apple = new Fruit("Apple");
        Fruit orange = new Fruit("Orange");
        Fruit papaya = new Fruit("Papaya");
        Fruit mango = new Fruit("Mango");
        Fruit grape = new Fruit("Grape");
        Fruit guava = new Fruit("Guava");

        list.addAll(apple, orange, papaya, mango, grape, guava);

        return list;
    }

    private void dragDetected(MouseEvent event, ListView<Fruit> listView)
    {
        // Make sure at least one item is selected
        int selectedCount = listView.getSelectionModel().getSelectedIndices().size();

        if (selectedCount == 0)
        {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        ArrayList<Fruit> selectedItems = this.getSelectedFruits(listView);

        ClipboardContent content = new ClipboardContent();
        content.put(FRUIT_LIST, selectedItems);

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event, ListView<Fruit> listView)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();

        if (event.getGestureSource() != listView && dragboard.hasContent(FRUIT_LIST))
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, ListView<Fruit> listView)
    {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasContent(FRUIT_LIST))
        {
            ArrayList<Fruit> list = (ArrayList<Fruit>)dragboard.getContent(FRUIT_LIST);
            listView.getItems().addAll(list);
            // Data transfer is successful
            dragCompleted = true;
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    private void dragDone(DragEvent event, ListView<Fruit> listView)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE)
        {
            removeSelectedFruits(listView);
        }

        event.consume();
    }

    private ArrayList<Fruit> getSelectedFruits(ListView<Fruit> listView)
    {
        // Return the list of selected Fruit in an ArratyList, so it is
        // serializable and can be stored in a Dragboard.
        ArrayList<Fruit> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        return list;
    }

    private void removeSelectedFruits(ListView<Fruit> listView)
    {
        // Get all selected Fruits in a separate list to avoid the shared list issue
        List<Fruit> selectedList = new ArrayList<>();

        for(Fruit fruit : listView.getSelectionModel().getSelectedItems())
        {
            selectedList.add(fruit);
        }

        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().removeAll(selectedList);
    }

    // Helper Method for Logging
    private void writelog(String text)
    {
        this.loggingArea.appendText(text + "\n");
    }
}

class Fruit implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name = "";

    public Fruit(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}