package com.li;

public class MyLinkedList {
    int length;
    ListNode head;
    ListNode tail;

    int i = 0;

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                synchronized (myLinkedList) {
                    while (myLinkedList.i == 1) {
                        try {
                            myLinkedList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t1: " + myLinkedList.i);
                    myLinkedList.i = 1;
                    myLinkedList.notify();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                synchronized (myLinkedList) {
                    while (myLinkedList.i == 0) {
                        try {
                            myLinkedList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t2: " + myLinkedList.i);
                    myLinkedList.i = 0;
                    myLinkedList.notify();
                }
            }
        }).start();
    }

    public MyLinkedList() {
        // 创建长度，双向链表，方便进行长度判定和尾插
        this.length = 0;
        this.head = new ListNode(-1, null, null);
        this.tail = new ListNode(-1, head, null);
        head.nxt = tail;
    }

    public int get(int index) {
        if (index >= this.length) {
            return -1;
        }
        return getNod(index).val;
    }

    public void addAtHead(int val) {
        ListNode node = new ListNode(val, this.head, this.head.nxt);
        this.head.nxt.pre = node;
        this.head.nxt = node;
        this.length++;
    }

    public void addAtTail(int val) {
        ListNode node = new ListNode(val, this.tail.pre, this.tail);
        this.tail.pre.nxt = node;
        this.tail.pre = node;
        this.length++;
    }

    public void addAtIndex(int index, int val) {
        if (index > this.length) {
            return;
        }
        ListNode cur = getNod(index);
        ListNode node = new ListNode(val, cur.pre, cur);
        cur.pre.nxt = node;
        cur.pre = node;
        this.length++;
    }

    public void deleteAtIndex(int index) {
        if (index >= this.length) {
            return;
        }
        ListNode cur = getNod(index);
        cur.pre.nxt = cur.nxt;
        cur.nxt.pre = cur.pre;
        this.length--;
    }

    public ListNode getNod(int index) {
        // 如果大于一半，从后面往前找
        if (index > (this.length >> 1)) {
            ListNode node = this.tail;
            for (index = this.length - index; index > 0; index--) {
                node = node.pre;
            }
            return node;
        }
        ListNode node = this.head;
        for (; index >= 0; index--) {  // 注意有头节点和尾节点
            node = node.nxt;
        }
        return node;
    }
}

class ListNode {
    int val;
    ListNode nxt;
    ListNode pre;

    public ListNode(int val, ListNode pre, ListNode nxt) {
        this.val = val;
        this.nxt = nxt;
        this.pre = pre;
    }
}