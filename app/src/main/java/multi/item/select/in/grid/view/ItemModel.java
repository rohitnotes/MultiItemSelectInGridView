package multi.item.select.in.grid.view;

public class ItemModel
{
    private String text;
    private int image;

    public ItemModel() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ItemModel(String text, int image) {
        this.text = text;
        this.image = image;
    }
}
