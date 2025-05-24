public class NoAvl {
    private int altura;
    private int no;
    private NoAvl filho_esquerdo;
    private NoAvl filho_direito;
    private int recorrencia;

    public NoAvl(int valorNo) {
        this.no = valorNo;
        this.filho_esquerdo = null;
        this.filho_direito = null;
        this.recorrencia = 1;
    }

    public int getAltura() {

        return altura;
    }

    public void setAltura(int altura) {

        this.altura = altura;
    }

    public int getNo() {

        return no;
    }

    public void setValorNo(int valor) {

        this.no = valor;
    }

    public NoAvl getFilhoEsquerdo() {

        return filho_esquerdo;
    }

    public void setFilhoEsquerdo(NoAvl noEsquerdo) {
        this.filho_esquerdo = noEsquerdo;
    }

    public NoAvl getFilhoDireito() {
        return filho_direito;
    }

    public void setFilhoDireito(NoAvl noDireito) {
        this.filho_direito = noDireito;
    }

    public int getRecorrencia() {
        return recorrencia;
    }

    public void adicionaRecorrencia() {
        this.recorrencia = getRecorrencia() + 1;
    }

    public void diminuiRecorrencia() {
        this.recorrencia = getRecorrencia() - 1;
    }
}