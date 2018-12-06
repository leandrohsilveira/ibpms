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
                    <button if={showUpdateButton} type="button" onclick={edit}>Editar</button>
                </td>
            </tr>
        </tbody>
    </table>

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