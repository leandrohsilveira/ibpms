<app-products-route>

    <h2>Produtos</h2>
    <app-products if={!loading} products={products} />
    <loading class="loading" if={loading} />

    <style>
        .loading {
            width: 100%;
        }
    </style>

    <script>

        function SearchObservable(page, size, sort) {
            
            riot.observable(this);

            this.page = page;
            this.size = size;
            this.sort = sort;
            
            this.update = (newState) => {
                console.log(newState);
                this.trigger('update', newState);
                Object.keys(newState).forEach(key => {
                    if(newState[key] !== undefined) this[key] = newState[key];
                })
                this.trigger('updated');
            }

        }

        this.products = [];
        this.loading = true;
        this.search = new SearchObservable(1, 10, 'name,asc');

        this.search.on('updated', () => {
            this.update({loading: true});
            fetch(`/api/products?page=${this.search.page}&size=${this.search.size}&sort=${this.search.sort}`)
                .then(response => {
                    return response.json().then(({items:products, count}) => {
                        this.update({products});
                    });
                })
                .finally(() => {
                    this.update({loading: false});
                })
        });

        this.on('route', () => {
            const q = route.query();
            this.search.update({
                page: q.page,
                size: q.size,
                sort: q.sort
            })
        });

    </script>

</app-products-route>