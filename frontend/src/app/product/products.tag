<app-products>
    <table>
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Preço</th>
                <th if={showActionsColumn}></th>
            </tr>
        </thead>
        <tbody>
            <tr each={opts.products}>
                <td>{ uuid }</td>
                <td>{ name }</td>
                <td>{ price }</td>
                <td if={showActionsColumn}>
                    <button if={showUpdateButton} class="button button-outline" type="button" onclick={edit}>Editar</button>
                    <button class="button button-danger" type="button">Remover</button>
                </td>
            </tr>
        </tbody>
    </table>

    <style>
        :scope tbody .button {
            height: 3rem;
            line-height: 3rem;
            padding: 0 10px;
        }
    </style>

    <script>
        this.showUpdateButton = false;
        this.showActionsColumn = false;

        this.edit = (e) => {
            opts.oneditclick(e.item);
        }

        this.on('mount', () => {
            const showUpdateButton = typeof opts.oneditclick === 'function';
            this.update({
                showUpdateButton,
                showActionsColumn: showUpdateButton
            });
        });

    </script>

</app-products>