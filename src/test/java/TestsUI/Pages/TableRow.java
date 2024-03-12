package TestsUI.Pages;

public class TableRow {
    private int price;
    private int count;
    private int total;

    public TableRow(String price, String count, String total) {

        this.price = Integer.parseInt(price.split(" ")[1]);
        this.count = Integer.parseInt(count);
        this.total = Integer.parseInt(total.split(" ")[1]);
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "{" + "price=" + price +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
