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
                <td>{ numeral(price).format('$ 0,0.00') }</td>
                <td if={showActionsColumn}>
                    <button if={showUpdateButton} class="button button-outline" type="button" onclick={handleEditClick}>Editar</button>
                    <button if={showDeleteButton} class="button button-danger button-outline" onclick={handleDeleteClick} type="button">Remover</button>
                </td>
            </tr>
            <tr if={!opts.products || !opts.products.length}>
                <td colspan={showActionsColumn ? 4 : 3} class="empty-result">
                    Nenhum produto encontrado.
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

        :scope tr > td.empty-result {
            text-align: center;
        }

    </style>

    <script>
        this.showUpdateButton = false;
        this.showActionsColumn = false;
        this.showDeleteButton = false;

        this.handleEditClick = (e) => {
            opts.oneditclick(e.item);
        }

        this.handleDeleteClick = (e) => {
            opts.ondeleteclick(e.item);
        }

        this.on('mount', () => {
            const showUpdateButton = typeof opts.oneditclick === 'function';
            const showDeleteButton = typeof opts.ondeleteclick === 'function';
            this.update({
                showUpdateButton,
                showDeleteButton,
                showActionsColumn: showUpdateButton || showDeleteButton,
            });
        });

    </script>

</app-products>