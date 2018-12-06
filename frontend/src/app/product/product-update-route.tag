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
                    route('/');
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
                        })
                    } else {
                        route('/');
                    }
                })
                .finally(() => {
                    this.update({loading: false});
                });
        });

    </script>
</app-product-update-route>