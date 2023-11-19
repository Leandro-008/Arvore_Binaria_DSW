import java.util.Random;

class NoArvore {
    int valor;
    NoArvore esquerda;
    NoArvore direita;

    public NoArvore(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }
}

public class ArvoreBinariaBalanceada {
    NoArvore raiz;

    // Função para inserir um valor na árvore
    public NoArvore inserir(NoArvore raiz, int valor) {
        if (raiz == null) {
            return new NoArvore(valor);
        }

        if (valor < raiz.valor) {
            raiz.esquerda = inserir(raiz.esquerda, valor);
        } else {
            raiz.direita = inserir(raiz.direita, valor);
        }

        return raiz;
    }

    // Função para realizar a rotação à direita
    private NoArvore rightRotate(NoArvore root) {
        if (root == null || root.esquerda == null) {
            return root;
        }

        NoArvore newRoot = root.esquerda;
        root.esquerda = newRoot.direita;
        newRoot.direita = root;
        return newRoot;
    }

    // Função para realizar a rotação à esquerda
    private NoArvore leftRotate(NoArvore root) {
        if (root == null || root.direita == null) {
            return root;
        }

        NoArvore newRoot = root.direita;
        root.direita = newRoot.esquerda;
        newRoot.esquerda = root;
        return newRoot;
    }

    // Função para realizar o balanceamento DSW
    private NoArvore balanceDSW(NoArvore root) {
        if (root == null) {
            return null;
        }

        // Fase 1: Realizar rotações à esquerda
        while (root.esquerda != null) {
            root = rightRotate(root);
        }

        NoArvore pseudoRoot = new NoArvore(0);
        pseudoRoot.direita = root;

        // Fase 2: Realizar rotações à direita
        int m = countNodes(root);
        int i = m / 2;

        while (i > 1) {
            pseudoRoot = leftRotate(pseudoRoot);
            i /= 2;
        }

        // Fase 3: Realizar rotações à direita em sequência
        NoArvore current = pseudoRoot;
        while (m > 1) {
            current = rightRotate(current);
            m /= 2;
        }

        return pseudoRoot.direita;
    }

    // Função para contar o número de nós na árvore
    private int countNodes(NoArvore root) {
        if (root == null) {
            return 0;
        }

        return 1 + countNodes(root.esquerda) + countNodes(root.direita);
    }

    // Função para imprimir a árvore em ordem
    public void inOrdem(NoArvore no) {
        if (no != null) {
            inOrdem(no.esquerda);
            System.out.print(no.valor + " ");
            inOrdem(no.direita);
        }
    }

    // Função para criar uma árvore com números aleatórios
    public void criarArvoreAleatoria(int quantidade) {
        Random random = new Random();
        for (int i = 0; i < quantidade; i++) {
            int valor = random.nextInt(101);
            raiz = inserir(raiz, valor);
        }
    }

    public static void main(String[] args) {
        ArvoreBinariaBalanceada arvore = new ArvoreBinariaBalanceada();
        ArvoreBinariaBalanceada arvore2 = new ArvoreBinariaBalanceada();

        // Criar uma árvore com 100 números aleatórios de 0 a 100
        arvore.criarArvoreAleatoria(100);

        // Imprimir a árvore balanceada
        System.out.println("Árvore balanceada em ordem:");
        arvore.inOrdem(arvore.raiz);

        // Implementar o Algoritmo DSW para construir uma árvore binária balanceada
        arvore.raiz = arvore.balanceDSW(arvore.raiz);

        // Criar uma árvore com mais 20 números aleatorios de 0 a 100
        arvore2.criarArvoreAleatoria(120);

        // Imprimir a nova árvore balanceada
        System.out.println("\nÁrvore balanceada com mais 20 números em ordem:");
        arvore2.inOrdem(arvore2.raiz);

        // Implementar o Algoritmo DSW para construir uma árvore binária balanceada
        arvore2.raiz = arvore.balanceDSW(arvore2.raiz);

    }
}
