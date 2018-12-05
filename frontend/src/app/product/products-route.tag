<app-products-route>

    <h2>Produtos</h2>
    <app-products products={products} />

    <script>
        this.products = [];

        this.on('mount', () => {
            fetch('/api/products?page=1&size=10&sort=name,asc')
                .then(response => {
                    return response.json().then(({items:products}) => {
                        this.update({products});
                    });
                });
        });

    </script>

</app-products-route>