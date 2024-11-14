package edu.grinnell.csc207.util;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

/**
 * Simple binary trees.
 *
 * @author Samuel A. Rebelsky
 * @author David William Stroud
 * @author Sheilla Muligande
 *
 * @param <T>
 *   The type of value stored in the tree.
 */
public class BinaryTree<T> implements Iterable<T> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The root of the tree.
   */
  BinaryTreeNode<T> root;

  /**
   * The number of values in the tree.
   */
  int size;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty, tree.
   */
  public BinaryTree() {
    this.size = 0;
    this.root = null;
  } // BinaryTree

  /**
   * Create a new, somewhat balanced, tree.
   *
   * @param values
   *   The values to put in the tree.
   */
  public BinaryTree(T[] values) {
    this.size = values.length;
    this.root = makeTree(values, 0, values.length);
  } // BinaryTree(T[])

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Prints out elements in approximately the same order as
   * given to the constructor.
   * @param pen The PrintWriter to use for output.
   */
  public void elements01(PrintWriter pen) {
    this.root.elements01(pen);
    pen.print("\n");
  } // elements01(PrintWriter)


  /**
   * Prints out elements via a depth-first, left-to-right, in-order
   * traversal.
   * @param pen The PrintWriter to use for output.
   */
  public void elements02(PrintWriter pen) {
    this.elements01(pen);
  } // elements02(PrintWriter)

  /**
   * Print all of the elements in some order or other.
   * 
   * Note: We are trying to avoid recursion.
   */
  @SuppressWarnings({ "unchecked" })
  public void print(PrintWriter pen) {
    BinaryTreeNode<T>[] nodes = (BinaryTreeNode<T>[]) java.lang.reflect.Array.newInstance(this.root.getClass(), this.size);
    int curIndex = 0;
    nodes[0] = this.root;
    while(curIndex < nodes.length){
      int i, j;
      for(i = curIndex; i < nodes.length && nodes[i] != null; i++){
        pen.print(nodes[i].value + " ");
      } // for
      for(j = curIndex, curIndex = i; j < curIndex; j++){
        if (nodes[j].left != null) {
          nodes[i++] = nodes[j].left;
        } // if
        if (nodes[j].right != null) {
          nodes[i++] = nodes[j].right;
        } // if
      } // for
    } // while
    pen.print("\n");
  } // print(PrintWriter)

  /**
   * Dump the tree to some output location.
   *
   * @param pen
   *   The output location.
   */
  public void dump(PrintWriter pen) {
    dump(pen, root, "");
  } // dump(PrintWriter)

  /**
   * Get an iterator for the tree.
   *
   * @return the iterator.
   */
  public Iterator<T> iterator() {
    Stack<BinaryTreeNode<T>> remaining = new Stack<BinaryTreeNode<T>>();
    remaining.push(this.root);

    return new Iterator<T>() {
      public boolean hasNext() {
        return !remaining.isEmpty();
      } // hasNext()

      public T next() {
        BinaryTreeNode<T> node = remaining.pop();
        if (node.left != null) {
          remaining.push(node.left);
        } // if
        if (node.right != null) {
          remaining.push(node.right);
        } // if
        return node.value;
      } // next()
    }; // new Iterator()
  } // iterator()

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Dump a portion of the tree to some output location.
   *
   * @param pen
   *   Where to dump the output.
   * @param node
   *   The node to dump.
   * @param indent
   *   How far to indent the dumped values.
   */
  void dump(PrintWriter pen, BinaryTreeNode<T> node, String indent) {
    if (node == null) {
      pen.println(indent + "<>");
    } else {
      pen.println(indent + node.value);
      if ((node.left != null) || (node.right != null)) {
        dump(pen, node.left, indent + "  ");
        dump(pen, node.right, indent + "  ");
      } // if has children
    } // else
  } // dump

  /**
   * Build a tree from a subarray from lb (inclusive) to ub (exclusive).
   *
   * @param values
   *   The array from which to draw values.
   * @param lb
   *   The lower bound of the subarray (inclusive).
   * @param ub
   *   The upper bound of the subarray (exclusive).
   *
   * @return
   *   The root of the newly made tree.
   */
  BinaryTreeNode<T> makeTree(T[] values, int lb, int ub) {
    if (ub <= lb) {
      return null;
    } else if (ub - lb == 1) {
      return new BinaryTreeNode<T>(values[lb]);
    } else {
      int mid = lb + (ub - lb) / 2;
      return new BinaryTreeNode<T>(values[mid], makeTree(values, lb, mid),
          makeTree(values, mid + 1, ub));
    } // if/else
  } // makeTree(T[], int, int)

} // class BinaryTree
