import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        String command = inputArray[0];

        String accountHolder = "";
        Account account = new Account("G",0);
        Employee employee = new Employee("J");

        while(!(command.equalsIgnoreCase("Close")))
        {

        switch(command)
        {
            case "Create":
                accountHolder = inputArray[1];
                account = bank.getAccountByHolder(accountHolder);

                String accountType = inputArray[2];
                double initialDeposit = Double.parseDouble(inputArray[3]);
                
                if(accountType.equals("Fixed Deposit"))
                {
                    int maturityPeriod = Integer.parseInt(inputArray[4]);
                    bank.createAccount(accountHolder, accountType, initialDeposit, maturityPeriod);
                }

                else
                {
                    bank.createAccount(accountHolder, accountType, initialDeposit, 0);
                }
                break;


            case "Deposit":
                double amount = Double.parseDouble(inputArray[1]);
                bank.depositexe(account.getAccountHolder(), amount);
                break;
            case "Withdraw":
                amount = Double.parseDouble(inputArray[1]);
                bank.withdrawexe(accountHolder, amount);
                break;
            case "Query":
                bank.queryexe(accountHolder);
                break;

            case "Request": 
                amount = Double.parseDouble(inputArray[1]);
                bank.requestLoanExe(accountHolder, amount);
                break;

            case "Close":

                if( employee != null )
                    {
                        System.out.println("Operations for "+ employee.getEmployeeName() +"closed");
                    }
                else if (account != null)
                    {
                        System.out.println("Transaction Closed for "+ account.getAccountHolder());
                    }

                break;

            case "Open":
                if(bank.getEmployeeByName(inputArray[1]) != null)
                {
                    employee = bank.getEmployeeByName(inputArray[1]);

                    if( employee instanceof Officer || employee instanceof ManagingDirector)
                    {
                        System.out.println(employee.getEmployeeName()+" active, there are loan approvals pending");
                    }
                    else
                    {
                        System.out.println(employee.getEmployeeName()+"  active");
                    }
                }
                else if(bank.getAccountByHolder(inputArray[1]) != null)
                {
                    account = bank.getAccountByHolder(inputArray[1]);
                    accountHolder = account.getAccountHolder();
                    System.out.println("Welcome back, " + account.getAccountHolder());
                }

                break;
            
            case "INC":
                bank.inc();
                break;

            case "Approve":
                if(inputArray[1].equals("Loan"))
                {
                    if(bank.getApproveLoanFlag() == true)
                    {
                        bank.approveLoanExe(employee);
                        bank.setApproveLoanFlag(false);
                    }
                    else
                    {
                        System.out.println("No loan request to be approved");
                    }
                }
                break;

            case "Lookup":
                accountHolder = inputArray[1];
                employee.lookup(accountHolder, bank);
                break;

            case "Change":
                String type = inputArray[1]; 
                double newInterestRate = Double.parseDouble(inputArray[2]);
                bank.changeInterestRateExe(employee, newInterestRate, type);
                break;

            case "See":
                employee.seeInternalFund(bank);
                break;

            default:
                System.out.println("Invalid command");
        }
    }

    scanner.close();
}
}
