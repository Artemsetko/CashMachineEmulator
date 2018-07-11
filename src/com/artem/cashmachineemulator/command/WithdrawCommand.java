package com.javarush.task.task26.task2613.command;

import com.artem.cashmachineemulator.CashMachine;
import com.artem.cashmachineemulator.ConsoleHelper;
import com.artem.cashmachineemulator.CurrencyManipulator;
import com.artem.cashmachineemulator.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;

import java.util.ResourceBundle;

class WithdrawCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".com.artem.cashmachineemulator.resources.withdraw");
    @Override
    public void execute() throws InterruptOperationException
    {

        ConsoleHelper.writeMessage("Enter currency code");
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int sum;
        while(true)
        {
            ConsoleHelper.writeMessage(res.getString("before"));
            String s = ConsoleHelper.readString();

            try {
                sum = Integer.parseInt(s);

            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                continue;
            }

            if (sum <= 0) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }

            if (!currencyManipulator.isAmountAvailable(sum)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }

            try {
                currencyManipulator.withdrawAmount(sum);
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }

            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, currencyCode));
            break;

        }

    }
}

