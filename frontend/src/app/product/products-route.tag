<app-products-route>

    <h2>Produtos</h2>
    
    <app-products-filter onfilter={filter} />
    <app-products if={!loading} products={products} />
    <app-paginator if={!loading} page={search.page} count={count} size={search.size} onpaginationchange={handlePaginationChange} />
    <loading class="loading" if={loading} />

    <style>
        :scope .loading {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>

    <script>

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

        this.products = [];
        this.count = 0;
        this.loading = true;
        this.search = new SearchObservable(1, 10, 'name,asc');

        this.filter = (values) => {
            this.search.update({
                filter: {
                    name: values.name,
                    uuid: values.uuid
                }
            });
        }

        this.handlePaginationChange = (obj) => {
            this.search.update(obj);
        }

        this.on('before-mount', () => this.search.on('updated', this.searchUpdated));
        this.on('unmount', () => this.search.off('updated', this.searchUpdated));

        this.searchUpdated = () => {
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
            const q = route.query();
            this.search.update({
                page: q.page && +q.page,
                size: q.size && +q.size,
                sort: q.sort
            })
        });

    </script>

</app-products-route>