<app-products-route>

    <h2>Produtos</h2>
    <button type="button" onclick={create}>Cadastrar produto</button>
    <app-products-filter onfilter={filter} />
    <app-products if={!loading} products={products} oneditclick={handleEditClick} />
    <app-paginator if={!loading} page={search.page} count={count} size={search.size} onpaginationchange={handlePaginationChange} />
    <app-loading class="loading" if={loading} />

    <style>
        :scope .loading {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>

    <script>

        this.products = [];
        this.count = 0;
        this.loading = true;
        this.search = new SearchObservable(1, 10, 'name,asc');

        this.filter = (values) => {
            this.search.update({
                page: 1,
                filter: values
            });
        }

        this.handlePaginationChange = (obj) => {
            this.search.update(obj);
        }

        this.handleEditClick = (product) => {
            route(`/edit/${product.uuid}`);
        }

        this.on('before-mount', () => this.search.on('updated', this.fetchProducts));
        this.on('unmount', () => this.search.off('updated', this.fetchProducts));

        this.create = () => {
            route('/create');
        };

        this.fetchProducts = () => {
            this.update({loading: true});
            fetch(`/api/products?${this.search.getQueryString()}`)
                .then(response => {
                    return response.json().then(({items:products, count}) => {
                        this.update({products, count});
                    });
                })
                .finally(() => {
                    this.update({loading: false});
                })
        };

        this.on('route', () => {
            this.fetchProducts();
        });

        function SearchObservable(page, size, sort) {
            
            riot.observable(this);

            this.page = page;
            this.size = size;
            this.sort = sort;
            this.filter = {};
            
            this.update = (newState) => {
                this.trigger('update', newState);
                Object.keys(newState).forEach(key => {
                    if(newState[key] !== undefined) this[key] = newState[key];
                })
                this.trigger('updated');
            }

            this.getQueryString = () => {
                const params = {
                    page: this.page,
                    size: this.size,
                    sort: this.sort
                };

                Object.keys(this.filter).forEach(key => {
                    if(this.filter[key] !== undefined) params[key] = this.filter[key]
                });

                return Object.keys(params).map(name => `${name}=${params[name]}`).join('&');
            }

        }
    </script>

</app-products-route>