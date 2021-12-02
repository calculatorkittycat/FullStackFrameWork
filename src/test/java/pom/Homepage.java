package pom;

import io.cucumber.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.BrowserUtil;
import utility.Driver;

import java.util.ArrayList;
import java.util.List;

public class Homepage {

    @FindBy(xpath = "//h2")
    private WebElement users;

    @FindBy(xpath = "(//h2)[2]")
    private WebElement books;

    @FindBy(xpath = "(//h2)[3]")
    private WebElement borrowedBooks;

    public Homepage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    public List<Integer> getNumsFromUi(){
        List<Integer> list = new ArrayList<>();

        BrowserUtil.waitForVisibility(By.xpath("(//h2)[1]"),5);

        int numberOfUsers = Integer.parseInt(this.books.getText());
        list.add(numberOfUsers);

        int numberOfBooks = Integer.parseInt(this.users.getText());
        list.add(numberOfBooks);

        int numberOfBorrowedBooks = Integer.parseInt(this.borrowedBooks.getText());
        list.add(numberOfBorrowedBooks);


        return list;
    }
}
