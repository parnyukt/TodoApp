package com.tanya.todoapp.model;

/**
 * Created by tatyana on 16.09.15.
 */
public enum TodoState {
    Undone("undone"),
    Done("done"),
    Overdue("overdue");

    final String t;
    TodoState(String tt) {
            t = tt;
        }

        public String getValue() { return t; }

        public static boolean isValid(TodoState s) {
            return (s== TodoState.Undone || s == TodoState.Done || s == TodoState.Overdue);
        }

        public static TodoState from(String str) {
            for(TodoState s: values()) {
                if(isValid(s) && s.getValue().equals(str)) {
                    return s;
                }
            }

            return Undone;
        }

        public static TodoState from(int i) {
            for(TodoState s: values()) {
                if(s.ordinal() == i) {
                    return s;
                }
            }

            return Undone;
        }

}
