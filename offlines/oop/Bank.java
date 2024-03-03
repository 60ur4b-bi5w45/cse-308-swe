import java.util.HashMap;

class Bank {
    private static HashMap<String, Account> accounts;
    private HashMap<String, Employee> employees;
    private double internalFund;
    private boolean approveLoanFlag;
    private int clock; // Assume all accounts are created at the same time

    public Bank() {
        accounts = new HashMap<String, Account>();
        employees = new HashMap<String, Employee>();
        internalFund = 1000000;
        approveLoanFlag = false;
        clock = 0;
        // creating MD, O1, O2, C1, C2, C3, C4, C5

        System.out.print("Bank Created;");
        
        employees.put("M D", new Employee("M D"));

        for(int i = 1; i <= 2; i++){
            employees.put("O" + i, new Officer("O" + i));
        }

        for(int i = 1; i <= 5; i++){
            employees.put("C" + i, new Employee("C" + i));
        }

        System.out.println(" M D, S1, S2, C1, C2, C3, C4, C5 created");
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public double getInternalFund() {
        return internalFund;
    }

    public boolean getApproveLoanFlag() {
        return approveLoanFlag;
    }

    public void setApproveLoanFlag(boolean approveLoanFlag) {
        this.approveLoanFlag = approveLoanFlag;
    }

    public void setInternalFund(double internalFund) {
        this.internalFund = internalFund;
    }

    public void inc() {
        
        clock++;

        if(clock == 1){
            System.out.println(clock + " year passed");
        }
        else{
            System.out.println(clock + " years passed");
        }

        double totalLoan = 0;

        for (Account account : accounts.values()) {
            account.addInterest();
            account.deductServiceCharge();
            account.yearlyDeductionForLoan();
            totalLoan += account.getLoan();
        }

        internalFund += totalLoan * 0.1; // 10% of the total loan amount is added to the internal fund 
    }

    public void createAccount(String accountHolder, String accountType, double initialDeposit, int maturityPeriod) {
        if (getAccountByHolder(accountHolder) == null) {
            Account account;
            switch (accountType.toLowerCase()) {
                case "savings":
                    account = new SavingsAccount(accountHolder, initialDeposit);
                    break;
                case "student":
                    account = new StudentAccount(accountHolder, initialDeposit);
                    break;
                case "fixed deposit":
                    account = new FixedDepositAccount(accountHolder, initialDeposit, maturityPeriod);
                    break;
                default:
                    System.out.println("Invalid account type");
                    return;
            }
            accounts.put(accountHolder, account);
        }
        else{
            System.out.println("Account already exists for " + accountHolder);
        }
    }

     public Account getAccountByHolder(String accountHolder) {
        return accounts.get(accountHolder);
    }

    public Employee getEmployeeByName(String emp) {
        return employees.get(emp);
    }

    public void depositexe (String accountHolder, double amount)
    {
        Account account = getAccountByHolder(accountHolder);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account does not exist for " + accountHolder);
        }
    }

    public void withdrawexe (String accountHolder, double amount)
    {
        Account account = getAccountByHolder(accountHolder);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account does not exist for " + accountHolder);
        }
    }

    public void queryexe (String accountHolder)
    {
        Account account = getAccountByHolder(accountHolder);
        if (account != null) {
           account.queryDeposit();
        } else {
            System.out.println("Account does not exist for " + accountHolder);
        }
    }

    public void requestLoanExe(String accountHolder, double loanAmount)
    {
        Account account = getAccountByHolder(accountHolder);
        if (account != null) {
            account.requestLoan(loanAmount);
        } else {
            System.out.println("Account does not exist for " + accountHolder);
        }

        approveLoanFlag = true;
    }

    public void approveLoanExe(Employee employee)
    {
        if(approveLoanFlag == true){
            employee.approveLoan(this);
        }
        else{
            System.out.println("Loan request not approved");
        }
    }

    public void changeInterestRateExe(Employee employee, double newInterestRate, String type)
    {
        if(type.equals("Savings") || type.equals("Student") || type.equals("Fixed Deposit")){
            employee.changeInterestRate(newInterestRate, this);
        }
        else{
            System.out.println("Invalid account type");
        }
    }
}