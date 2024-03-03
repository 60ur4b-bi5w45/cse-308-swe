class StudentAccount extends Account {
    private static double INTEREST_RATE = 0.05;

    public static void setInterestRate(double newInterestRate) {
        INTEREST_RATE = newInterestRate;
    }

    public StudentAccount(String accountHolder, double initialDeposit) {
        super(accountHolder, initialDeposit);
        System.out.println("Student account for " + getAccountHolder() + " created; initial balance " + getBalance() + "$");
    }

    @Override
    public void requestLoan(double loanAmount) {
        if (loanAmount <= 1000) {
            super.requestLoan(loanAmount);
        } else {
            System.out.println("Request exceeds maximum allowable loan. You can request up to 1000$");
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        deductServiceCharge();
    }

    @Override
    public void addInterest() {
        double interest = getBalance() * INTEREST_RATE;
        deposit(interest);
    }

    @Override
    public void deductServiceCharge() {}

    @Override
    public void yearlyDeductionForLoan() {
        double deduction = getLoan() * 0.1;
        withdraw(deduction);
    }
}