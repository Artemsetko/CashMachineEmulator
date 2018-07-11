package com.javarush.task.task26.task2613.command;

import com.artem.cashmachineemulator.CashMachine;
import com.artem.cashmachineemulator.ConsoleHelper;
import com.artem.cashmachineemulator.CurrencyManipulator;
import com.artem.cashmachineemulator.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".com.artem.cashmachineemulator.resources.deposit");
    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String cc = ConsoleHelper.askCurrencyCode();
        String[] cash = ConsoleHelper.getValidTwoDigits(cc);
        try {
            int amount = Integer.parseInt(cash[0])*Integer.parseInt(cash[1]);
            CurrencyManipulatorFactory.getManipulatorByCurrencyCode(cc).addAmount(Integer.parseInt(cash[0]), Integer.parseInt(cash[1]));
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, cc));
        }
        catch (NumberFormatException e)
        {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
