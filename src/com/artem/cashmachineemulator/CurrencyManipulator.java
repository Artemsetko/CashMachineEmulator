package com.artem.cashmachineemulator;


import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    String currencyCode;
    Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


    public void addAmount(int denomination, int count) {

        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int rez = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            rez += (key * value);
        }
        return rez;
    }

    public boolean hasMoney() {
        return denominations.size() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        if (expectedAmount > 0)
            return expectedAmount <= getTotalAmount();
        return false;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;
        HashMap<Integer, Integer> temp = new HashMap<>();
        temp.putAll(denominations);

        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> pair : temp.entrySet())
            list.add(pair.getKey());

        Collections.sort(list);
        Collections.reverse(list);

        TreeMap<Integer, Integer> result = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        // outer:
        do {
            for (Integer aList : list) {
                int key = aList;
                int value = temp.get(key);
                while (true) {
                    if (sum < key || value <= 0) {
                        temp.put(key, value);
                        // sum = expectedAmount;
                        //  continue outer;
                        break;
                    }
                    sum -= key;
                    value--;

                    if (result.containsKey(key))
                        result.put(key, result.get(key) + 1);
                    else
                        result.put(key, 1);
                }
            }
            if (sum == 0) break;
            sum = expectedAmount;
            result.clear();
            temp.clear();
            temp.putAll(denominations);
            list.remove(0);
        } while (list.size() != 0);


        if (sum > 0 && list.size() == 0)
            throw new NotEnoughMoneyException();
        else {
           /* for (Map.Entry<Integer, Integer> pair : result.entrySet())
                ConsoleHelper.writeMessage(pair.getKey() + " - " + pair.getValue());*/

            denominations = new HashMap<>(temp);
         //   ConsoleHelper.writeMessage("Transaction complete successfully");
        }
        return result;
    }


}