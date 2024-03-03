class FixedDepositAccount extends Account {
    private static double INTEREST_RATE = 0.15;
    private int maturityPeriod;

    public static void setInterestRate(double newInterestRate) {
        INTEREST_RATE = newInterestRate;
    }

    public FixedDepositAccount(String accountHolder, double initialDeposit, int maturityPeriod) {
        super(accountHolder, initialDeposit);
        this.maturityPeriod = maturityPeriod;
        System.out.println("Fixed Deposit account for " + getAccountHolder() + " created; initial balance " + getBalance() + "$");
    }

    @Override
    public void requestLoan(double loanAmount) {
        if (loanAmount <= 100000) {
            super.requestLoan(loanAmount);
        } else {
            System.out.println("Request exceeds maximum allowable loan. You can request up to 100000$");
        }
    }

    @Override
    public void yearlyDeductionForLoan() {
        double deduction = getLoan() * 0.1;
        withdraw(deduction);
    }

    @Override
    public void withdraw(double amount) {
        if (this.getAgeOfAccount() >= maturityPeriod) {
            super.withdraw(amount);
        } else {
            System.out.println("Invalid transaction; current balance " + getBalance() + "$");
        }
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