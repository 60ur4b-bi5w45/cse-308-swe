class Officer extends Employee {
    public Officer(String employeeName) {
        super(employeeName);
    }

    @Override
    public void approveLoan(Bank bank) {
        System.out.println("Loan for ___" + " approved");
    }
}