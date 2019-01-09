package com.payencai.library.rbtree;



public class RBTree<T extends Comparable<T>> {
    private RBNode<T> mRoot;
    private static final Boolean BLACK=true;
    private static final Boolean RED=false;

    public class RBNode<T extends Comparable<T>>{
        public RBNode<T> mParent;
        public RBNode<T> mLeft;
        public RBNode<T> mRight;
        public T mKey;
        public boolean mColor;
        public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
            mKey = key;
            mColor = color;
            mParent = parent;
            mLeft = left;
            mRight = right;
        }
    }
    private void leftRotate(RBNode<T> rbNode){
        /**
         * 选择分三步进行的，每个变换节点都要指定新的父节点
         * 同时一些节点也要指定对应的左右儿子
         */
        //先记录下当前节点的右儿子
        RBNode<T> rightNode=rbNode.mRight;
        //将当前节点的右儿子设置为当前节点的右儿子的左儿子
        rbNode.mRight=rightNode.mLeft;
        //将当前节点的右儿子的左儿子的父亲设置为当前节点
        if(rightNode.mLeft!=null){
            rightNode.mLeft.mParent=rbNode;
        }
        //将y的parent设置为x的parent
        rightNode.mParent=rbNode.mParent;
        if(rbNode.mParent==null) {
            this.mRoot = rightNode;
        }else{
            if(rbNode.mParent.mLeft==rbNode){
                rbNode.mParent.mLeft=rightNode;
            }else{
                rbNode.mParent.mRight=rightNode;
            }
        }
        //
        rightNode.mLeft=rbNode;
        rbNode.mParent=rightNode;

    }
    private void rightRotate(RBNode<T> rbNode){
        //先记录下当前节点的左儿子
        RBNode<T> leftNode=rbNode.mLeft;
        rbNode.mLeft=leftNode.mRight;
        if(leftNode.mRight!=null){
            leftNode.mRight.mParent=rbNode.mLeft;
        }
        leftNode.mParent=rbNode.mParent;

        if(rbNode.mParent==null) {
            this.mRoot = leftNode;
        }else{
            if(rbNode.mParent.mLeft==rbNode){
                rbNode.mParent.mLeft=leftNode;
            }else{
                rbNode.mParent.mRight=leftNode;
            }
        }
        //
        leftNode.mRight=rbNode;
        rbNode.mParent=leftNode;
    }
    private void insert(RBNode<T> node){
        int cmp;
        RBNode<T> y=null;
        RBNode<T> x=this.mRoot;
        //要用y记录的原因是因为当最后x遍历到底部时，已经指向null,而x的parent后面作为拆入节点的父亲
        //从根节点开始对比查找如果比key大x指针
        while(x!=null){
            y=x;//y相当于一个指针记录下合适插入节点的位置
            cmp = node.mKey.compareTo(x.mKey);
            if(cmp<0){
                x=x.mLeft;
            }else{
                x=x.mRight;
            }
        }
        node.mParent=y;
        //父子的关系的链接是双向建立的，所以也要为插入的节点的父节点指定左儿子或者指定右儿子
        if(y!=null){
             cmp=node.mKey.compareTo(y.mKey);
            if(cmp<0){
                y.mLeft=node;
            }else{
                y.mRight=node;
            }
        }else{
            this.mRoot=node;
        }
        node.mColor=RED;
        //修正按照二叉树插入的问题使其成为满足条件的红黑树
        insertFixUp(node);
    }
    private void insertFixUp(RBNode<T> node){
        RBNode<T>  parent,gparent;
        if(node!=null)
        while((parent=node.mParent)!=null&&!parent.mColor){
            gparent=parent.mParent;
            //满足这个条件后要经过下面三步逐步修复，因为没一次修复会出现新的问题不满足红黑树的条件，
            // 所以当前节点不是不变的，它要逐步向上进行左旋和右旋，最终使得二叉树变成红黑树
            if(gparent!=null&&parent==gparent.mLeft){
                RBNode<T> uncle=gparent.mRight;
                if(uncle!=null&&!uncle.mColor){
                    //从当前节点的祖父节点作为下一次修复的当前节点
                    parent.mColor=BLACK;
                    uncle.mColor=BLACK;
                    gparent.mColor=RED;
                    node=gparent;
                    continue;
                }
                if(parent.mRight==node){
                    RBNode<T> temp;
                    leftRotate(parent);
                    temp=parent;
                    parent=node;
                    node=temp;
                }
                parent.mColor=BLACK;
                gparent.mColor=RED;
                rightRotate(gparent);
            }else if(gparent!=null&&parent==gparent.mRight){
                RBNode<T> uncle=gparent.mLeft;
                if(uncle!=null&&!uncle.mColor){
                    //从当前节点的祖父节点作为下一次修复的当前节点
                    parent.mColor=BLACK;
                    uncle.mColor=BLACK;
                    gparent.mColor=RED;
                    node=gparent;
                    continue;
                }
                if(parent.mLeft==node){
                    RBNode<T> temp;
                    rightRotate(parent);
                    temp=parent;
                    parent=node;
                    node=temp;
                }
                parent.mColor=BLACK;
                gparent.mColor=RED;
                leftRotate(gparent);
            }
        }
        this.mRoot.mColor=BLACK;
    }
}
