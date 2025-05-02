public class AddCategoryUI extends UI {
    @Override
    public UI run() {
        clear();

        println("Add new category");
        newLine();

        char code;

        while (true) {
            code = readCharOrDefault("Enter category's character code (Empty to cancel): ", ' ');

            if (code == ' ') {
                return null;
            }
            else if (Category.validateCode(code)) {
                if (CategoryManager.getCategory(code) == null) {
                    acceptInput();
                    break;
                }
                else {
                    rejectInput("Category code already exists!");
                }
            }
            else {
                rejectInput("Invalid code! Code must be uppercase alphabet.");
            }
        }

        CategoryManager.addCategory(new Category(code, noReject().readString("Enter category name: ")));

        newLine();
        readEnter("Category added successfully! Press Enter to return to main menu");

        return null;
    }
}
