<app-product-update-route>
    <h2>Atualizar produto</h2>
    <app-product-form if={!loading} onproductsubmit={handleSubmit} oncancel={cancel} product={product} />
    <app-loading if={loading} />
    <script>
        this.uuid = null;
        this.loading = true;
        this.product = null;

        this.handleSubmit = (values) => {
            this.update({loading: true});
            fetch(`/api/products/${this.uuid}`, {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json'
                }),
                body: JSON.stringify(values)
            })
            .then(response => {
                if(response.status < 300) {
                    window.ibpms.message.dispatch('Produto atualizado com sucesso');
                    route('/');
                } else {
                    window.ibpms.message.dispatch('Ocorreu um erro ao atualizar o produto');
                }
            })
            .finally(() => {
                this.update({loading: false});
            })
        }

        this.cancel = () => {
            route('/');
        };

        this.on('route', uuid => {
            this.update({uuid, loading: true});
            fetch(`/api/products/${uuid}`)
                .then(response => {
                    if(response.status < 400) {
                        return response.json().then(product => {
                            this.update({product});
                        });
                    } else {
                        if(response.status == 404) {
                            window.ibpms.message.dispatch('Produto não encontrado');
                        } else {
                            window.ibpms.message.dispatch('Ocorreu um erro ao obter os dados do produto');
                        }
                        route('/');
                    }
                })
                .finally(() => {
                    this.update({loading: false});
                });
        });

    </script>
</app-product-update-route>