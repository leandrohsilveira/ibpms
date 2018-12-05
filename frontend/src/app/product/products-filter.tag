<app-products-filter>
    <form onsubmit={ filter }>
        <fieldset>
            <div class="row">
                <div class="column">
                    <label for="name">ID</label>
                    <input type="text" name="uuid" ref="uuid">
                </div>
                <div class="column">
                    <label for="name">Nome</label>
                    <input type="text" name="name" ref="name">
                </div>
                <div class="column">
                    <label for="sort">Ordernar por</label>
                    <select name="sort" ref="sort">
                        <option selected value="name,asc">Por nome crescente</option>
                        <option value="name,desc">Por nome decrescente</option>
                        <option value="uuid,asc">Por ID crescente</option>
                        <option value="uuid,desc">Por ID decrescente</option>
                        <option value="price,asc">Por preço crescente</option>
                        <option value="price,desc">Por preço decrescente</option>
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
                uuid: this.refs.uuid.value,
                name: this.refs.name.value,
                sort: this.refs.sort.value
            }
            if(typeof opts.onfilter === 'function') opts.onfilter(values);
        }
    </script>

</app-products-filter>