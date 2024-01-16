package com.xhh.smalldemocommon.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestNodeV2 {
    public static void main(String[] args) {
        List<Long> longList = new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        longList.add(3L);
        longList.add(4L);
//        longList.add(5L);
//        longList.add(6L);
//        longList.add(7L);
        List<NodeV2> nodeList = new ArrayList<>();
//        longList.forEach((Long it) -> {
//            Node node = new Node();
//            Node pre = new Node();
//            Node next = new Node();
//            
//            node.value = it;
//            node.pre = pre;
//            node.next = next;
//            
//            nodeList.add(node);
//        });

        for (int i = 0; i < longList.size(); i++) {
            if (i == 0) {
                NodeV2 node = new NodeV2();
                NodeV2 pre = new NodeV2();
                NodeV2 next = new NodeV2();
                node.value = longList.get(i);
                pre.value = longList.get(longList.size() - 1);
                next.value = longList.get(i + 1);
                node.pre = pre;
                node.next = next;
                nodeList.add(node);
            } else if (i == longList.size() - 1) {
                NodeV2 node = new NodeV2();
                NodeV2 pre = new NodeV2();
                NodeV2 next = new NodeV2();
                node.value = longList.get(i);
                pre.value = longList.get(i - 1);
                next.value = longList.get(0);
                node.pre = pre;
                node.next = next;
                nodeList.add(node);
            } else {
                NodeV2 node = new NodeV2();
                NodeV2 pre = new NodeV2();
                NodeV2 next = new NodeV2();
                node.value = longList.get(i);
                pre.value = longList.get(i - 1);
                next.value = longList.get(i + 1);
                node.pre = pre;
                node.next = next;
                nodeList.add(node);
            }

        }

//        System.out.println(nodeList);
//        Optional<Node> first = nodeList.stream().filter((Node n) -> n.value == 1L).findFirst();
//        Node node = first.get();
//        if (node.next.value == 3L) {
//            System.out.println(true);
//        } else {
//            System.out.println(false);
//        }
        
        CalculationRulesV2 calculationRules = new CalculationRulesV2();
        List<Long> points = new ArrayList<>();
//        points.add(1L);
//        points.add(2L);
//        points.add(3L);
//        points.add(4L);
//        points.add(1L);
//        points.add(2L);
//        points.add(3L);
//        points.add(4L);
//        points.add(1L);
//        points.add(2L);
//        points.add(3L);
//        points.add(4L);
        
        
        
//        points.add(2L);
//        points.add(1L);
//        points.add(2L);
//        points.add(3L);
//        points.add(4L);
//        points.add(1L);
//        points.add(5L);
//        points.add(2L);
//        points.add(3L);
//        points.add(4L);
//        points.add(1L);
//        points.add(2L);
        
        points.add(3L);
        points.add(4L);
        points.add(1L);
        points.add(2L);
        points.add(3L);
        points.add(4L);
        points.add(1L);
        points.add(2L);
        points.add(3L);
        points.add(4L);
        points.add(1L);
        points.add(2L);
        points.add(3L);
        
        
//        points.add(1L);
//        points.add(2L);
//        points.add(2L);
//        points.add(1L);
//        points.add(2L);
        Integer num = 0;
        for (int i = 0; i < points.size(); i++) {
            Long point = points.get(i);
//            if (i == 0) {
//                Optional<Node> first = nodeList.stream().filter((Node n) -> n.value.equals(point)).findFirst();
//                if (first.isPresent()) {
//                    Node node = first.get();
//                    boolean equals = node.next.value.equals(points.get(i + 1));
//                    if (equals) {
//                        calculationRules.num = calculationRules.num + 1;
//                        calculationRules.first = points.get(0);
//                    } else {
//                        calculationRules.num = 0;
//                        calculationRules.first = points.get(i + 1);
//                    }
//                } else {
//                    calculationRules.first = points.get(i+1);
//                }
//            } else 
            if (i == points.size() - 1) {
                Optional<NodeV2> first = nodeList.stream().filter((NodeV2 n) -> n.value.equals(point)).findFirst();
                if (first.isPresent()) {
                    NodeV2 node = first.get();
                    boolean equals = node.pre.value.equals(points.get(points.size() - 2));
                    if (equals) {
                        calculationRules.num = calculationRules.num + 1;
                    } else {
                        calculationRules.num = 0;
                        calculationRules.first = points.get(0);
                    }
                }
            } else {
                Optional<NodeV2> first = nodeList.stream().filter((NodeV2 n) -> n.value.equals(point)).findFirst();
                if (first.isPresent()) {
                    NodeV2 node = first.get();
                    if (calculationRules.next) {
                        boolean equals = node.next.value.equals(points.get(i + 1));
                        if (equals) {
                            calculationRules.num = calculationRules.num + 1;
                            if (i == 0) {
                                calculationRules.first = points.get(0);
                            }
                        } else {
                            boolean nextEquals = node.pre.value.equals(points.get(i + 1));
                            if (nextEquals) {
                                calculationRules.num =  1;
                                calculationRules.next = false;
                                calculationRules.first = points.get(i);
                                if (i == 0) {
                                    calculationRules.first = points.get(0);
                                }
                            } else {
                                calculationRules.num = 0;
                                calculationRules.first = points.get(i + 1);
                            }
                        }
                    } else {
                        boolean preEquals = node.pre.value.equals(points.get(i + 1));
                        if (preEquals) {
                            calculationRules.num = calculationRules.num + 1;
                        } else {
                            boolean nextEquals = node.next.value.equals(points.get(i + 1));
                            if (nextEquals) {
                                calculationRules.num = 1;
                                calculationRules.next = true;
                                calculationRules.first = points.get(i);
                            } else {
                                calculationRules.num = 0;
                                calculationRules.first = points.get(i  + 1);
                            }
                        }
                    }
                } else {
                    calculationRules.first = points.get(i+1);
                }
            }

            if (i == points.size() - 1) {
                if (calculationRules.num >= 4 && calculationRules.first.equals(points.get(i))) {
                    num += 1;
                    calculationRules.num = 0;
                }
            } else {
                if (calculationRules.num >= 4 && calculationRules.first.equals(points.get(i+1))) {
                    num += 1;
                    calculationRules.num = 0;
                }
            }
//            if (calculationRules.num >= 2 && calculationRules.first.equals(point)) {
//                num += 1;
//                calculationRules.num = 1;
//            }
        }

        System.out.println(num);
    }
}


@Data
class NodeV2 {
    NodeV2 pre;
    NodeV2 next;
    Long value;
    Long mills;
}

@Data
class CalculationRulesV2 {
    Integer num = 0;
    Long first = null;
    // 正序
    boolean next = true;
}
