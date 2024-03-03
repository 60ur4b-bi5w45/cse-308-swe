class Employee {
    private String employeeName;

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void lookup(String accountHolder, Bank bank) {
        Account account = bank.getAccountByHolder(accountHolder);
        System.out.println(account.getAccountHolder() + "'s current balance" + account.getBalance()+ "$");
    }

    public void approveLoan(Bank bank) {
        System.out.println("You don’t have permission for this operation");
    }

    public void changeInterestRate(double newInterestRate, Bank bank) {
        System.out.println("You don’t have permission for this operation");
    }
    
    public void seeInternalFund (Bank bank) {
        System.out.println("You don’t have permission for this operation");
    }
}