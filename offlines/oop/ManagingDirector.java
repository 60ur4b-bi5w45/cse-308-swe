class ManagingDirector extends Officer {
    public ManagingDirector(String employeeName) {
        super(employeeName);
    }

    @Override
    public void seeInternalFund (Bank bank) {
        System.out.println("Internal fund of is "+ bank.getInternalFund()+ "$");
    }

    public void changeInterestRate(double newInterestRate, Bank bank, String accountType) {
        if (accountType.equals("FixedDepositAccount")) {
            FixedDepositAccount.setInterestRate(newInterestRate);
        } else if (accountType.equals("SavingsAccount")) {
            SavingsAccount.setInterestRate(newInterestRate);
        } else if (accountType.equals("StudentAccount")) {
            StudentAccount.setInterestRate(newInterestRate);
        }
        System.out.println("Interest rate changed to "+ newInterestRate);
    }
}


