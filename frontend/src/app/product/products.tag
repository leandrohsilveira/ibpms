<app-products>
    <table>
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Preço</th>
            </tr>
        </thead>
        <tbody>
            <tr each={opts.products}>
                <td>{ uuid }</td>
                <td>{ name }</td>
                <td>{ price }</td>
            </tr>
        </tbody>
    </table>
</app-products>