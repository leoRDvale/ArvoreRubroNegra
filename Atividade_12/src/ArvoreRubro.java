import java.util.LinkedList;
import java.util.Queue;

public class ArvoreRubro {
    private NoRubro raiz;

    public ArvoreRubro() {
        this.raiz = null;
    }

    public NoRubro getRaiz() {
        return raiz;
    }

    public void setRaiz(int valor) {
        raiz = AdicionaNo(raiz, valor);
        if (raiz != null) {
            raiz.setVermelho(false);
        }
    }

    private NoRubro AdicionaNo(NoRubro no, int valor) {
        if (no == null) {
            return new NoRubro(valor);
        }

        if (valor < no.getNo()) {
            no.setFilhoEsquerdo(AdicionaNo(no.getFilhoEsquerdo(), valor));
        } else if (valor > no.getNo()) {
            no.setFilhoDireito(AdicionaNo(no.getFilhoDireito(), valor));
        } else {
            no.adicionaRecorrencia();
        }

        if (getVermelho(no.getFilhoDireito()) && !getVermelho(no.getFilhoEsquerdo())) {
            no = rotacaoEsquerda(no);
        }
        if (getVermelho(no.getFilhoEsquerdo()) && getVermelho(no.getFilhoEsquerdo().getFilhoEsquerdo())) {
            no = rotacaoDireita(no);
        }
        if (getVermelho(no.getFilhoEsquerdo()) && getVermelho(no.getFilhoDireito())) {
            inversarCor(no);
        }

        return no;
    }

    public void remover(int valor) {
        raiz = removerNo(raiz, valor);
        if (raiz != null) {
            raiz.setVermelho(false);
        }
    }

    private NoRubro removerNo(NoRubro no, int valor) {
        if (no == null) {
            return null;
        }
        if (valor < no.getNo()) {
            if (no.getFilhoEsquerdo() == null || (!getVermelho(no.getFilhoEsquerdo()) && (no.getFilhoEsquerdo().getFilhoEsquerdo() == null || !getVermelho(no.getFilhoEsquerdo().getFilhoEsquerdo())))) {
                no = moverDoisParaDireita(no);
            }
            no.setFilhoEsquerdo(removerNo(no.getFilhoEsquerdo(), valor));
        } else {
            if (getVermelho(no.getFilhoEsquerdo())) {
                no = rotacaoDireita(no);
            }
            if (valor == no.getNo() && no.getFilhoDireito() == null) {
                return no.getFilhoEsquerdo();
            }
            if (no.getFilhoDireito() == null || (!getVermelho(no.getFilhoDireito()) && (no.getFilhoDireito().getFilhoEsquerdo() == null || !getVermelho(no.getFilhoDireito().getFilhoEsquerdo())))) {
                no = moverDoisParaDireita(no);
            }
            if (valor == no.getNo()) {
                if (no.getFilhoDireito() == null) {
                    return no.getFilhoEsquerdo();
                }
                NoRubro x = minimo(no.getFilhoDireito());
                no.setNo(x.getNo());
                no.setFilhoDireito(removerNo(no.getFilhoDireito(), x.getNo()));
            } else {
                no.setFilhoDireito(removerNo(no.getFilhoDireito(), valor));
            }
        }

        return balancear(no);
    }

    private NoRubro balancear(NoRubro no) {
        if (getVermelho(no.getFilhoDireito())) {
            no = rotacaoEsquerda(no);
        }
        if (getVermelho(no.getFilhoEsquerdo()) && getVermelho(no.getFilhoEsquerdo().getFilhoEsquerdo())) {
            no = rotacaoDireita(no);
        }
        if (getVermelho(no.getFilhoEsquerdo()) && getVermelho(no.getFilhoDireito())) {
            inversarCor(no);
        }

        return no;
    }

    private NoRubro moverDoisParaDireita(NoRubro no) {
        inversarCor(no);
        if (no.getFilhoEsquerdo() != null && getVermelho(no.getFilhoEsquerdo().getFilhoEsquerdo())) {
            no = rotacaoDireita(no);
            inversarCor(no);
        }
        return no;
    }

    private NoRubro minimo(NoRubro no) {
        while (no.getFilhoEsquerdo() != null) {
            no = no.getFilhoEsquerdo();
        }
        return no;
    }

    private NoRubro rotacaoEsquerda(NoRubro no) {
        NoRubro filhoDireito = no.getFilhoDireito();
        no.setFilhoDireito(filhoDireito.getFilhoEsquerdo());
        filhoDireito.setFilhoEsquerdo(no);
        filhoDireito.setVermelho(no.getVermelho());
        no.setVermelho(true);
        return filhoDireito;
    }

    private NoRubro rotacaoDireita(NoRubro no) {
        NoRubro filhoEsquerdo = no.getFilhoEsquerdo();
        no.setFilhoEsquerdo(filhoEsquerdo.getFilhoDireito());
        filhoEsquerdo.setFilhoDireito(no);
        filhoEsquerdo.setVermelho(no.getVermelho());
        no.setVermelho(true);
        return filhoEsquerdo;
    }

    private void inversarCor(NoRubro no) {
        no.setVermelho(!no.getVermelho());
        if (no.getFilhoEsquerdo() != null) {
            no.getFilhoEsquerdo().setVermelho(!no.getFilhoEsquerdo().getVermelho());
        }
        if (no.getFilhoDireito() != null) {
            no.getFilhoDireito().setVermelho(!no.getFilhoDireito().getVermelho());
        }
    }

    private boolean getVermelho(NoRubro no) {
        return no != null && no.getVermelho();
    }

    public int buscar(int valor) {
        return buscarRecorrencia(this.raiz, valor);
    }

    private int buscarRecorrencia(NoRubro no, int valor) {
        if (no == null) {
            return 0;
        }
        if (valor < no.getNo()) {
            return buscarRecorrencia(no.getFilhoEsquerdo(), valor);
        } else if (valor > no.getNo()) {
            return buscarRecorrencia(no.getFilhoDireito(), valor);
        } else {
            return no.getRecorrencia();
        }
    }

    public void imprimirArvore(NoRubro raiz) {
        if (raiz == null) {
            return;
        }
        Queue<NoRubro> nos = new LinkedList<>();
        nos.add(raiz);

        while (!nos.isEmpty()) {
            NoRubro no = nos.poll();
            if(no.getVermelho()) {
                System.out.println(no.getNo() + " - No Vermelho");
            } else {
                System.out.println(no.getNo() +  " - No Preto");
            }

            if (no.getFilhoEsquerdo() != null) {
                nos.add(no.getFilhoEsquerdo());
            }

            if (no.getFilhoDireito() != null) {
                nos.add(no.getFilhoDireito());
            }
        }
    }
}