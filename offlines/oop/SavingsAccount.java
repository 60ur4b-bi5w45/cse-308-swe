class SavingsAccount extends Account {
    private static double INTEREST_RATE = 0.10;

    public static void setInterestRate(double newInterestRate) {
        INTEREST_RATE = newInterestRate;
    }

    public SavingsAccount(String accountHolder, double initialDeposit) {
        super(accountHolder, initialDeposit);
        System.out.println("Savings account for " + getAccountHolder() + " created; initial balance " + getBalance() + "$");
    }

    @Override
    public void requestLoan(double loanAmount) {
        if (loanAmount <= 10000) {
            super.requestLoan(loanAmount);
        } else {
            System.out.println("Request exceeds maximum allowable loan. You can request up to 10000$");
        }
    }

    @Override
    public void yearlyDeductionForLoan() {
        double deduction = getLoan() * 0.1;
        withdraw(deduction);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    @Override
    public void addInterest() {
        double interest = getBalance() * INTEREST_RATE;
        deposit(interest);
    }

    @Override
    public void deductServiceCharge() {
            deposit(-500);
        }
    }