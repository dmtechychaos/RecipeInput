import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RecipeInputApp extends JFrame {

    private List<Recipe> recipes;
    private JPanel recipePanel;
    private JTextField recipeNameField;
    private JTextArea ingredientsTextArea;
    private JTextArea instructionsTextArea;

    public RecipeInputApp() {
        recipes = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Recipe Input App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem retrieveItem = new JMenuItem("Retrieve Recipes");
        retrieveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRecipesDialog();
            }
        });
        fileMenu.add(retrieveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create the main panel containing the components
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create and add the components
        recipePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        recipePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        recipeNameField = new JTextField();
        ingredientsTextArea = new JTextArea();
        instructionsTextArea = new JTextArea();

        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsTextArea);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsTextArea);

        recipePanel.add(new JLabel("Recipe Name:"));
        recipePanel.add(recipeNameField);
        recipePanel.add(new JLabel("Ingredients:"));
        recipePanel.add(ingredientsScrollPane);
        recipePanel.add(new JLabel("Instructions:"));
        recipePanel.add(instructionsScrollPane);

        mainPanel.add(recipePanel, BorderLayout.CENTER);

        // Create and add the "Save Recipe" button
        JButton saveButton = new JButton("Save Recipe");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipe();
            }
        });

        mainPanel.add(saveButton, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }

    private void saveRecipe() {
        String recipeName = recipeNameField.getText();
        String ingredients = ingredientsTextArea.getText();
        String instructions = instructionsTextArea.getText();

        Recipe recipe = new Recipe(recipeName, ingredients, instructions);
        recipes.add(recipe);

        // Optionally, you can save the recipes to a file or database here.

        clearFields();
    }

    private void clearFields() {
        recipeNameField.setText("");
        ingredientsTextArea.setText("");
        instructionsTextArea.setText("");
        recipeNameField.requestFocus();
    }

    private void showRecipesDialog() {
        // Create a new dialog to display the list of recipes
        JDialog recipesDialog = new JDialog(this, "Retrieve Recipes", true);
        recipesDialog.setSize(400, 300);
        recipesDialog.setLocationRelativeTo(this);

        // Create a text area to show the recipes
        JTextArea recipesTextArea = new JTextArea();
        recipesTextArea.setEditable(false);

        // Create a search field to allow the user to input the recipe name search criteria
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 20));

        // Create a button to trigger the search
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchCriteria = searchField.getText();
                if (!searchCriteria.isEmpty()) {
                    displayMatchingRecipes(searchCriteria, recipesTextArea);
                }
            }
        });

        // Add the search components to a panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Recipe Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the saved recipes to the text area
        for (Recipe recipe : recipes) {
            recipesTextArea.append("Recipe Name: " + recipe.getName() + "\n");
            recipesTextArea.append("Ingredients:\n" + recipe.getIngredients() + "\n");
            recipesTextArea.append("Instructions:\n" + recipe.getInstructions() + "\n\n");
        }

        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(recipesTextArea);

        // Create a panel to hold the search components and the text area
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the dialog
        recipesDialog.add(mainPanel);

        // Show the dialog
        recipesDialog.setVisible(true);
    }

    private void displayMatchingRecipes(String searchCriteria, JTextArea recipesTextArea) {
        // Clear the text area
        recipesTextArea.setText("");

        // Display the recipes that match the search criteria (partial or complete name)
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(searchCriteria.toLowerCase())) {
                recipesTextArea.append("Recipe Name: " + recipe.getName() + "\n");
                recipesTextArea.append("Ingredients:\n" + recipe.getIngredients() + "\n");
                recipesTextArea.append("Instructions:\n" + recipe.getInstructions() + "\n\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecipeInputApp app = new RecipeInputApp();
            app.setVisible(true);
        });
    }
}

class Recipe {
    private String name;
    private String ingredients;
    private String instructions;

    public Recipe(String name, String ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
