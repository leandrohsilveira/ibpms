<app-products-filter>
    <form onsubmit={ filter }>
        <fieldset>
            <div class="row">
                <div class="column">
                    <label for="name">Código</label>
                    <input type="text" name="code" ref="code">
                </div>
                <div class="column">
                    <label for="name">Nome</label>
                    <input type="text" name="name" ref="name">
                </div>
                <div class="column">
                    <label for="sort">Ordernar por</label>
                    <select name="sort" ref="sort">
                        <option selected value="name,asc">Nome crescente</option>
                        <option value="name,desc">Nome decrescente</option>
                        <option value="code,asc">Código crescente</option>
                        <option value="code,desc">Código decrescente</option>
                        <option value="price,asc">Preço crescente</option>
                        <option value="price,desc">Preço decrescente</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div class="buttons">
                        <button type="submit" class="button button-outline">Filtrar</button>
                        <button type="reset" class="button button-clear">Limpar</button>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>

    <script>
        this.filter = (e) => {
            e.preventDefault();
            const values = {
                code: this.refs.code.value,
                name: this.refs.name.value,
                sort: this.refs.sort.value
            }
            if(typeof opts.onfilter === 'function') opts.onfilter(values);
        }
    </script>

</app-products-filter>