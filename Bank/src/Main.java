import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Account {
    private double balance;
    private String currency;
    private List<String> transactions = new ArrayList<>();
    public List<String> getTransactionHistory() {
        return transactions;
    }
    public Account(double initialBalance, String currency) {
        this.balance = initialBalance;
        this.currency = currency;
    }


    public class TransactionHistory {
        public void addTransaction(String transaction) {
            transactions.add(transaction);
        }


    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }
    public String getCurrency() {
        return currency;
    }
}


class BankCard {
    private Account ownerAccount;

    public BankCard(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public void makeTransaction(String transactionDetails) {

        String[] parts = transactionDetails.split(" ");
        double transactionAmount = Double.parseDouble(parts[parts.length - 2]);


        if (transactionAmount <= ownerAccount.getBalance()) {

            double newBalance = ownerAccount.getBalance() - transactionAmount;
            ownerAccount.setBalance(newBalance);


            ownerAccount.new TransactionHistory().addTransaction(transactionDetails);

            System.out.println("Транзакцію успішно завершено.");
        } else {
            System.out.println("Недостатньо коштів на рахунку. Транзакцію відхилено.");
        }
    }
}



class Person {
    private String name;
    private Account account;
    private BankCard bankCard;

    public Person(String name, Account account, BankCard bankCard) {
        this.name = name;
        this.account = account;
        this.bankCard = bankCard;
    }


    class Authorization {
        private String login;
        private String password;

        public Authorization(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public boolean authorize() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть логін: ");
            String inputLogin = scanner.nextLine();
            System.out.print("Введіть пароль: ");
            String inputPassword = scanner.nextLine();

            if (inputLogin.equals(login) && inputPassword.equals(password)) {
                System.out.println("Авторизація успішна!");
                return true;
            } else {
                System.out.println("Невірний логін або пароль. Авторизація відхилена.");
                return false;
            }
        }
    }

    public void showInfo() {
        System.out.println("Особа: " + name);
        System.out.println("Баланс: " + account.getBalance() + " " + account.getCurrency());
        System.out.println("Реквізити банківської картки: " + bankCard);
    }

    public String getName() {
        return name;
    }
}


class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}


public class Main {
    public static void main(String[] args) {
        Account account = new Account(1000, "USD");
        BankCard bankCard = new BankCard(account);
        Person person = new Person("Максимко Максим", account, bankCard);


        Person.Authorization authorization = person.new Authorization("Maks", "1234");


        if (authorization.authorize()) {
            person.showInfo();

            List<Product> products = new ArrayList<>();
            products.add(new Product("Ігрова мишка", 110));
            products.add(new Product("Механічна клавіатура", 150));

            Scanner scanner = new Scanner(System.in);
            for (Product product : products) {
                System.out.print("Введіть кількість для " + product.getName() + ": ");
                int quantity = scanner.nextInt();
                double totalCost = product.getPrice() * quantity;
                String transactionDetails = person.getName() + " [" + bankCard + "] - купив " +
                        product.getName() + " (" + quantity + ") " + totalCost + " " + account.getCurrency();
                bankCard.makeTransaction(transactionDetails);
            }

            List<String> transactionHistory = account.getTransactionHistory();
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }

            System.out.println("Баланс: " + account.getBalance() + " " + account.getCurrency());
        } else {
            System.out.println("Доступ відхилено. Програма завершує роботу.");
        }
    }
}