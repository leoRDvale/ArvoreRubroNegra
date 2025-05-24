public class NoRubro {
    private int no;
    private NoRubro filhoEsquerdo;
    private NoRubro filhoDireito;
    private boolean vermelho;
    private int recorrencia;

    public NoRubro(int valorNo) {
        this.no = valorNo;
        this.filhoEsquerdo = null;
        this.filhoDireito = null;
        this.vermelho = true;
        this.recorrencia = 1;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int valor) {
        this.no = valor;
    }

    public NoRubro getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    public void setFilhoEsquerdo(NoRubro noEsquerdo) {
        this.filhoEsquerdo = noEsquerdo;
    }

    public NoRubro getFilhoDireito() {
        return filhoDireito;
    }

    public void setFilhoDireito(NoRubro noDireito) {
        this.filhoDireito = noDireito;
    }

    public boolean getVermelho() {
        return vermelho;
    }

    public void setVermelho(boolean vermelho) {
        this.vermelho = vermelho;
    }

    public int getRecorrencia() {
        return recorrencia;
    }

    public void adicionaRecorrencia() {
        this.recorrencia = getRecorrencia() + 1;
    }

    public void RemoveRecorrencia() {
        this.recorrencia = getRecorrencia() - 1;
    }
}