package com.debal.java.test;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PuzzleSolve {
	private static int[] masterSet = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please provide input sequence:");
		String inputString = scanner.next();
		SLinkedList list = new SLinkedList();
		StringBuilder builder = new StringBuilder();
		Set<Character> uniqueElements = new HashSet<Character>();
		for (char c : inputString.toCharArray()) {
			uniqueElements.add(c);
			if (c == '+' || c == '=') {
				list.addElement(builder.toString().trim());
				builder = new StringBuilder();
			} else
				builder.append(c);
		}
		list.addElement(builder.toString().trim());
		int[] clonedMasterSet = new int[masterSet.length];
		System.arraycopy(masterSet, 0, clonedMasterSet, 0, masterSet.length);
		Set<Integer> solvedSet = new HashSet<Integer>();
		PuzzleSolve.solvePuzzle(uniqueElements, list, clonedMasterSet, solvedSet);
	}

	public static void solvePuzzle(Set<Character> uniqueElements, SLinkedList inputSequence, int[] masterSet, Set<Integer> solvedSet) throws Exception {
		if (uniqueElements.size() > masterSet.length)
			throw new Exception("Only ten unique letters are permitted in whole equation.");

	}

	private static void getSubsets(Set<Integer> superSet, int k, int idx, Set<Integer> current, List<Set<Integer>> solution) {
		// successful stop clause
		if (current.size() == k) {
			solution.add(new HashSet<Integer>(current));
			return;
		}
		// unseccessful stop clause
		if (idx == superSet.size())
			return;
		Integer x = -1;
		current.add(x);
		// "guess" x is in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
		current.remove(x);
		// "guess" x is not in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
	}
}
