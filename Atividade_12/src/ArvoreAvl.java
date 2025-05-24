import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.max;

public class ArvoreAvl {
    private NoAvl raiz;

    public ArvoreAvl() {
        this.raiz = null;
    }

    public NoAvl getRaiz() {
        return raiz;
    }

    public void setRaiz(int valNo) {
        this.raiz = insereNo(this.raiz, valNo);
    }

    public NoAvl insereNo(NoAvl no, int valor) {
        if (no == null) {
            no = new NoAvl(valor);
            return no;
        } else if (valor < no.getNo()) {
            no.setFilhoEsquerdo(insereNo(no.getFilhoEsquerdo(), valor));
        } else if (valor > no.getNo()) {
            no.setFilhoDireito(insereNo(no.getFilhoDireito(), valor));
        } else {
            no.adicionaRecorrencia();
            return no;
        }

        no = AtualizaAlturaInsercao(no);
        return no;
    }

    public void remocao(int valorNo) {
        this.raiz = removeNo(this.raiz, valorNo);
    }

    public NoAvl removeNo(NoAvl no, int val) {
        if (no == null) {
            return null;
        }

        if (val < no.getNo()) {
            no.setFilhoEsquerdo(removeNo(no.getFilhoEsquerdo(), val));
        } else if (val > no.getNo()) {
            no.setFilhoDireito(removeNo(no.getFilhoDireito(), val));
        } else {
            if (no.getRecorrencia() > 1) {
                no.diminuiRecorrencia();
                return no;
            }

            if (no.getFilhoEsquerdo() == null) {
                return no.getFilhoDireito();
            } else if (no.getFilhoDireito() == null) {
                return no.getFilhoEsquerdo();
            }

            NoAvl temp = minimoValorNo(no.getFilhoDireito());
            no.setValorNo(temp.getNo());
            no.setFilhoDireito(removeNo(no.getFilhoDireito(), temp.getNo()));
        }

        no = AtualizaAlturaRemocao(no);
        return no;
    }

    public NoAvl rotacaoLL(NoAvl no) {
        NoAvl filhoEsquerdo = no.getFilhoEsquerdo();
        no.setFilhoEsquerdo(filhoEsquerdo.getFilhoDireito());
        filhoEsquerdo.setFilhoDireito(no);

        no.setAltura(1 + max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())));
        filhoEsquerdo.setAltura(1 + max(altura(filhoEsquerdo.getFilhoEsquerdo()), altura(filhoEsquerdo.getFilhoDireito())));

        return filhoEsquerdo;
    }

    public NoAvl rotacaoRR(NoAvl no) {
        NoAvl filhoDireito = no.getFilhoDireito();

        if (filhoDireito == null) {
            return no;
        }

        no.setFilhoDireito(filhoDireito.getFilhoEsquerdo());
        filhoDireito.setFilhoEsquerdo(no);

        no.setAltura(1 + max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())));
        filhoDireito.setAltura(1 + max(altura(filhoDireito.getFilhoEsquerdo()), altura(filhoDireito.getFilhoDireito())));

        return filhoDireito;
    }

    public NoAvl rotacaoLR(NoAvl no) {
        no.setFilhoEsquerdo(rotacaoRR(no.getFilhoEsquerdo()));
        return rotacaoLL(no);
    }

    public NoAvl rotacaoRL(NoAvl no) {
        no.setFilhoDireito(rotacaoLL(no.getFilhoDireito()));
        return rotacaoRR(no);
    }

    private NoAvl AtualizaAlturaInsercao(NoAvl no) {
        no.setAltura(1 + max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())));

        int balanceamento = balandeamentoArvore(no);

        if (balanceamento > 1) {
            if (balandeamentoArvore(no.getFilhoEsquerdo()) >= 0) {
                no = rotacaoLL(no);
            } else {
                no.setFilhoEsquerdo(rotacaoRR(no.getFilhoEsquerdo()));
                no = rotacaoLL(no);
            }
        } else if (balanceamento < -1) {
            if (balandeamentoArvore(no.getFilhoDireito()) <= 0) {
                no = rotacaoRR(no);
            } else {
                no.setFilhoDireito(rotacaoLL(no.getFilhoDireito()));
                no = rotacaoRR(no);
            }
        }
        return no;
    }

    private NoAvl AtualizaAlturaRemocao(NoAvl no) {
        no.setAltura(1 + max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())));

        int balanceamento = balandeamentoArvore(no);

        if (balanceamento > 1) {
            if (balandeamentoArvore(no.getFilhoEsquerdo()) >= 0) {
                no = rotacaoLL(no);
            } else {
                no.setFilhoEsquerdo(rotacaoRR(no.getFilhoEsquerdo()));
                no = rotacaoLL(no);
            }
        } else if (balanceamento < -1) {
            if (balandeamentoArvore(no.getFilhoDireito()) <= 0) {
                no = rotacaoRR(no);
            } else {
                no.setFilhoDireito(rotacaoLL(no.getFilhoDireito()));
                no = rotacaoRR(no);
            }
        }
        return no;
    }

    public NoAvl minimoValorNo(NoAvl no) {
        NoAvl atual = no;
        while (atual.getFilhoEsquerdo() != null) {
            atual = atual.getFilhoEsquerdo();
        }
        return atual;
    }

    private int altura(NoAvl no) {
        if (no != null) {
            return no.getAltura();
        } else {
            return 0;
        }
    }

    private int balandeamentoArvore(NoAvl no) {
        if (no != null) {
            return altura(no.getFilhoEsquerdo()) - altura(no.getFilhoDireito());
        } else {
            return 0;
        }
    }

    public boolean getBalanceamento(NoAvl no) {
        if (no == null) {
            return true;
        }

        int balanceamento = balandeamentoArvore(no);
        return (balanceamento >= -1 && balanceamento <= 1) && getBalanceamento(no.getFilhoEsquerdo()) && getBalanceamento(no.getFilhoDireito());
    }

    public int buscarRecorrencia(int valor) {
        return recorrencia(this.raiz, valor);
    }

    private int recorrencia(NoAvl no, int valor) {
        if (no == null) {
            return 0;
        }
        if (valor < no.getNo()) {
            return recorrencia(no.getFilhoEsquerdo(), valor);
        } else if (valor > no.getNo()) {
            return recorrencia(no.getFilhoDireito(), valor);
        } else {
            return no.getRecorrencia();
        }
    }

    public void imprimirArvore(NoAvl raiz) {
        if (raiz == null) {
            return;
        }

        Queue<NoAvl> nos = new LinkedList<>();
        nos.add(raiz);

        while (!nos.isEmpty()) {
            int nivel = nos.size();
            while (nivel-- > 0) {
                NoAvl noAtual = nos.poll();
                System.out.printf("Valor: " + noAtual.getNo());

                if (noAtual.getFilhoEsquerdo() != null) {
                    nos.add(noAtual.getFilhoEsquerdo());
                }

                if (noAtual.getFilhoDireito() != null) {
                    nos.add(noAtual.getFilhoDireito());
                }
            }
            System.out.println();
        }
    }
}