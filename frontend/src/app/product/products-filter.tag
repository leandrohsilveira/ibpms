<app-products-filter>
    <form onsubmit={ filter }>
        <div class="row">
            <div class="column">
                <label for="name">ID</label>
                <input type="text" name="uuid" ref="uuid">
            </div>
            <div class="column">
                <label for="name">Nome</label>
                <input type="text" name="name" ref="name">
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
    </form>

    <script>
        this.filter = (e) => {
            e.preventDefault();
            const values = {
                uuid: this.refs.uuid.value,
                name: this.refs.name.value
            }
            if(typeof opts.onfilter === 'function') opts.onfilter(values);
        }
    </script>

</app-products-filter>