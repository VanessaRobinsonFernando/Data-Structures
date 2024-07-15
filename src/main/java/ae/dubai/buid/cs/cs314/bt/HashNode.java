package ae.dubai.buid.cs.cs314.bt;

public class HashNode<E extends Comparable<E>> {

    private AVLNode<E> root; // Change TreeNode to AVLNode

    public HashNode() {
        root = null;
    }

    // AVL tree node
    static class AVLNode<E> {
        E data;
        AVLNode<E> left;
        AVLNode<E> right;
        int height;

        public AVLNode(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public E getElement() {
            return data;
        }

        public AVLNode<E> getLeft() {
            return left;
        }

        public void setLeft(AVLNode<E> left) {
            this.left = left;
        }

        public AVLNode<E> getRight() {
            return right;
        }

        public void setRight(AVLNode<E> right) {
            this.right = right;
        }


    }

    public AVLNode<E> getRoot() {
        return root;
    }

    public AVLNode<E> insert(E element) {
        root = insert(root, element);
        return root; // Return the modified root after insertion
    }

    private AVLNode<E> insert(AVLNode<E> nd, E element) {
        if (nd == null) {
            return new AVLNode<>(element); // Create a new node for insertion
        } else if (element.compareTo(nd.getElement()) <= 0) {
            nd.setLeft(insert(nd.getLeft(), element));
        } else {
            nd.setRight(insert(nd.getRight(), element));
        }

        // Update the height of the current node
        nd = updateHeight(nd);

        // Check and balance the tree
        int balanceFactor = getBalanceFactor(nd);

        // Left heavy
        if (balanceFactor > 1) {
            // Left-Left case
            if (element.compareTo(nd.getLeft().getElement()) < 0) {
                return rightRotate(nd);
            }
            // Left-Right case
            else {
                nd.setLeft(leftRotate(nd.getLeft()));
                return rightRotate(nd);
            }
        }
        // Right heavy
        else if (balanceFactor < -1) {
            // Right-Right case
            if (element.compareTo(nd.getRight().getElement()) > 0) {
                return leftRotate(nd);
            }
            // Right-Left case
            else {
                nd.setRight(rightRotate(nd.getRight()));
                return leftRotate(nd);
            }
        }

        return nd; // Return the modified node
    }



    private AVLNode<E> updateHeight(AVLNode<E> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        int newHeight = 1 + Math.max(leftHeight, rightHeight);
        node.setHeight(newHeight);
        return node;
    }

    private int height(AVLNode<E> n) {
        return (n != null) ? n.getHeight() : 0;
    }

    private int getBalanceFactor(AVLNode<E> node) {
        return height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode<E> leftRotate(AVLNode<E> node) {
        AVLNode<E> newRoot = node.getRight();

        if (newRoot != null) {
            node.setRight(newRoot.getLeft());
            newRoot.setLeft(node);

            // Update heights
            updateHeight(node);
            updateHeight(newRoot);

            return newRoot;
        } else {
            return node;  // No rotation needed if newRoot is null
        }
    }

    private AVLNode<E> rightRotate(AVLNode<E> node) {
        AVLNode<E> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        // Update heights
        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    public boolean search(AVLNode<E> r, E val) {
        if (r == null)
            return false;
        boolean found = false;
        if (r.getElement().compareTo(val) == 0)
            return true;
        if (r.getElement().compareTo(val) > 0)
            return search(r.getLeft(), val);
        else
            return search(r.getRight(), val);
    }
}