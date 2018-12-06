<app-product-delete-route>
    <h2>Remover produto</h2>
    <fieldset if={!loading}>
        <p>Você tem certeza que deseja excluir o produto "{product.name}" com ID <strong>{product.uuid}</strong>? Esta ação é irreversível.</p>
        <div class="buttons">
            <button type="button" class="button button-danger" onclick={handleDeleteClick}>Remover o produto</button>
            <button type="button" class="button button-clear" onclick={handleCancelClick}>Manter o produto</button>
        </div>
    </fieldset>
    <app-loading if={loading}></app-loading>

    <script>
        this.uuid = null;
        this.product = null;
        this.loading = true;

        this.handleDeleteClick = (values) => {
            this.update({loading: true});
            fetch(`/api/products/${this.uuid}`, { method: 'DELETE' })
                .then(response => {
                    if(response.status < 300) {
                        window.ibpms.message.dispatch('Produto removido com sucesso');
                        route('/');
                    } else {
                        window.ibpms.message.dispatch('Ocorreu um erro ao remover o produto');
                    }
                })
                .finally(() => {
                    this.update({loading: false});
                })
        }

        this.handleCancelClick = () => {
            route('/');
        };

        this.on('route', uuid => {
            this.update({uuid, loading: true});
            fetch(`/api/products/${uuid}`)
                .then(response => {
                    if(response.status < 400) {
                        return response.json().then(product => {
                            this.update({product});
                        })
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

</app-product-delete-route>