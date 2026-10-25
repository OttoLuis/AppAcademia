# Regras de Negócio — FitIA

As regras de negócio definem as condições que determinam como o FitIA deve funcionar, incluindo validações, permissões e relacionamentos entre os dados.

> **Observação:** estas são regras recomendadas para o sistema. A presença de uma regra neste documento não significa que ela já esteja implementada no código.

## 1. Usuários

1. Cada usuário deve possuir um identificador único.
2. O e-mail deve ser único, impedindo cadastros duplicados.
3. A senha deve ser armazenada de forma segura, utilizando hash, como BCrypt.
4. Cada usuário deve acessar e alterar apenas os próprios dados, salvo permissões administrativas previstas.
5. Os endpoints protegidos devem exigir autenticação e respeitar as permissões configuradas.

## 2. Perfil físico

1. Cada usuário pode possuir apenas um perfil físico.
2. O perfil físico deve estar associado a um usuário existente.
3. Peso, altura e idade devem aceitar somente valores válidos.
4. O objetivo físico deve corresponder a uma opção permitida pelo sistema.
5. A frequência semanal de treino e o tempo disponível devem respeitar os limites definidos pela aplicação.
6. Os dados do perfil podem ser utilizados para personalizar sugestões de treino e, futuramente, de alimentação.

## 3. Metas

1. Cada meta deve estar associada a um usuário.
2. Um usuário pode possuir várias metas.
3. Não é permitido cadastrar uma meta sem um usuário válido.
4. O progresso deve respeitar um intervalo definido, por exemplo, de 0% a 100%.
5. A data de início e o prazo devem ser válidos.
6. O usuário só pode consultar ou modificar suas próprias metas, respeitando as permissões de acesso.

## 4. Planos de treino

1. Cada plano de treino deve estar associado ao usuário correto.
2. O plano deve considerar o objetivo físico, o nível de experiência, a frequência semanal e o tempo disponível.
3. Os exercícios devem ser compatíveis com os equipamentos disponíveis, quando essa informação for utilizada.
4. A duração e a distribuição dos treinos devem respeitar os parâmetros definidos pelo sistema.
5. O usuário não pode acessar ou modificar planos de outros usuários sem autorização.
6. Caso seja utilizada IA para analisar a evolução, as sugestões devem se basear nos dados disponíveis e não inventar resultados de desempenho.

## 5. Planos alimentares

Estas regras são propostas para a funcionalidade de dieta.

1. Cada plano alimentar deve pertencer a um usuário.
2. O plano pode considerar objetivo físico, peso, idade, altura, preferências e restrições alimentares.
3. Cada refeição deve estar vinculada a um plano alimentar.
4. As calorias e os macronutrientes apresentados devem corresponder aos alimentos e às quantidades cadastradas.
5. Os dados recebidos devem ser validados antes de salvar um plano.
6. O usuário deve poder consultar seu plano alimentar e, conforme as funcionalidades implementadas, atualizar suas preferências.
7. Sugestões geradas por IA devem ser tratadas como orientações gerais, não como substitutas de uma avaliação nutricional profissional.

## 6. Segurança e autorização

1. Endpoints privados exigem autenticação.
2. O sistema deve validar os dados de entrada.
3. Usuários não podem acessar dados pessoais de outras contas sem autorização.
4. Operações administrativas devem exigir permissões adequadas.
5. Senhas, segredos OAuth2 e chaves privadas não devem ser expostos no código ou no repositório.
6. Os erros devem retornar códigos HTTP coerentes, como:
   - `400 Bad Request`: dados inválidos.
   - `401 Unauthorized`: autenticação ausente ou inválida.
   - `403 Forbidden`: usuário autenticado sem permissão para acessar o recurso.

## 7. Responsabilidades das camadas

- **Controller:** recebe requisições HTTP e encaminha as operações. Deve evitar concentrar toda a lógica de negócio.
- **Service:** implementa as regras de negócio, como impedir um segundo perfil físico para o mesmo usuário ou validar a associação de uma meta.
- **Repository:** consulta e persiste os dados.
- **Banco de dados:** pode reforçar regras com restrições, como e-mail único e relacionamentos obrigatórios.

## 8. Prioridades de implementação

1. Impedir e-mails duplicados.
2. Garantir que cada usuário tenha somente um perfil físico.
3. Garantir que cada meta esteja vinculada a um usuário.
4. Impedir que usuários acessem metas, treinos e planos alimentares de outras contas.
5. Validar peso, altura, idade, progresso e demais campos antes de salvar.

---

**Nota:** ter um JWT válido não significa que o usuário pode acessar os dados de qualquer conta. A autorização deve verificar se o recurso solicitado pertence ao usuário autenticado ou se ele possui uma permissão administrativa apropriada.
