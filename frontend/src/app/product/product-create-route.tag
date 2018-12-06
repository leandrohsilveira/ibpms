<app-product-create-route>
    <h2>Cadastrar produto</h2>
    <app-product-form if={!loading} onproductsubmit={handleSubmit} oncancel={cancel} />
    <app-loading if={loading} />
    <script>
        this.loading = false;

        this.handleSubmit = (values) => {
            this.update({loading: true});
            fetch('/api/products', {
                method: 'POST',
                headers: new Headers({
                    'Content-Type': 'application/json'
                }),
                body: JSON.stringify(values)
            })
            .then(response => {
                if(response.status < 300) {
                    window.ibpms.message.dispatch('Produto cadastrado com sucesso');
                    route('/');
                } else {
                    window.ibpms.message.dispatch('Ocorreu um erro ao cadastrar o produto');
                }
            })
            .finally(() => {
                this.update({loading: false});
            })
        }

        this.cancel = () => {
            route('/');
        };
    </script>
</app-product-create-route>