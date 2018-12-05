<app-products-route>

    <h2>Produtos</h2>
    <app-products products={products} />

    <script>
        this.products = [];

        this.on('route', () => {
            const q = route.query();
            const page = +q.page || 1;
            const size = +q.size || 10;
            const sort = q.sort || 'name,asc';
            fetch(`/api/products?page=${page}&size=${size}&sort=${sort}`)
                .then(response => {
                    return response.json().then(({items:products}) => {
                        this.update({products});
                    });
                });
        });

    </script>

</app-products-route>