package com.parkingms.utils;

import com.parkingms.models.User;
import java.util.List;

public class DNFSorter {

    public static void sortByStatus(List<User> users) {
        int low = 0;
        int mid = 0;
        int high = users.size() - 1;

        while (mid <= high) {
            int statusVal = statusToInt(users.get(mid).getPayStatus());

            if (statusVal == 0) {
                swap(users, low, mid);
                low++;
                mid++;
            } else if (statusVal == 1) {
                mid++;
            } else {
                swap(users, mid, high);
                high--;
            }
        }
    }

    private static int statusToInt(String status) {
        if (status == null) return 1;
        switch (status.toUpperCase()) {
            case "REJECTED":  return 0;
            case "PENDING":   return 1;
            case "COMPLETED": return 2;
            default:          return 1;
        }
    }

    private static void swap(List<User> list, int i, int j) {
        User temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
