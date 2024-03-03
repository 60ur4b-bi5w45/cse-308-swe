class Account {
    private String accountHolder; // Name of the account holder
    private double balance;
    private int ageOfAccount; // Age of the account in years

    private double loan; // Loan amount in dollars

    public Account(String accountHolder, double initialDeposit) {
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.ageOfAccount = 0;
        this.loan = 0;
    }

    public void setLoan(double loan) {
        this.loan += loan;
    }

    public double getLoan() {
        return loan;
    }

    public int getAgeOfAccount() {
        return ageOfAccount;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + "$ deposited; current balance " + balance + "$");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful; current balance " + balance + "$");
        } else {
            System.out.println("Invalid transaction; current balance " + balance + "$");
        }
    }

    public void requestLoan(double loanAmount) {
        System.out.println("Loan request successful, sent for approval");
    }

    public void queryDeposit() {
        System.out.println("Current balance " + balance + "$");
    }

    public void addInterest() {
        // To be implemented in the subclasses
    }

    public void deductServiceCharge() {
        // To be implemented in the subclasses
    }

    public void yearlyDeductionForLoan() {
        // To be implemented in the subclasses
    }
}

