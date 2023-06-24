# rmi-chat

rmi-chat é uma proposta de sistema de chat que permite a comunicação em tempo real entre usuários. É projetado para facilitar a troca de mensagens e promover a interação entre os participantes de um canal.

Os clientes podem criar salas de bate-papo ou ingressar em salas existentes - chamadas de canais, ou channels -, onde podem interagir com outros participantes. O sistema de chat possibilita a visualização das mensagens enviadas pelos outros usuários, permitindo uma comunicação eficiente e em tempo real.

Conta com uma camada de armazenamento, que é utilizada somente para recuperação do histórico de mensagens de um canal. 

# detalhes técnicos
- Plataforma Java
- Utilização de Java RMI.
- Redis

# características
O sistema foi pensado para atender aos critérios que definem um sistema distribuído, que são:

Escalabilidade: os servidores podem escalar tanto horizontalmente quanto verticalmente, e nosso armazenamento é realizado em memória por uma instancia de Redis, mas que facilmente pode ser expandida para uma arquitetura com clusters distribuídos. Vale ressaltar que experimentos realizados (acompanhamento de uso dos recursos em tempo real, a medida que novos clients se conectam) indicam que a exigência de recursos não aumenta de forma exponencial, e no máximo de forma linear. Em favor da escalabilidade, o histórico de um canal pode ser apagado regularmente.

Abertura: o sistema é aberto porque provê interfaces públicas e documentadas (via java-doc), seguindo protocolos de comunicação padronizados com java-rmi e TCP, e possibilita a criação de clients a partir de software e hardware heterogêneos, por ser executada na plataforma Java. Além disso possibilita a expansão dos clients e criação dos mais diversos aplicativos de chat em cima da aplicação.

Transparência: O sistema oferece transparência de localização, replicação e escalabilidade, a medida que várias instancias do servidor de chat podem existir, como réplicas, e serem implantadas em qualquer lugar. Também oferece transparência de concorrência, pois o chat acontece em tempo real, onde n clientes podem se conectar, solicitar histórico de conversa e enviar mensagens.

Compartilhamento de recursos: O recurso compartilhado aqui é o histórico de mensagens, que é replicado com o auxílio de uma rotina de sincronização implementada com RMI, em uma estrutura Redis. O histórico pode ser acessado e modificado concorrentemente, e a sincronização vai acontecer. A estratégia de controle de tempos define que o timestamp da mensagem é gerado pelo servidor que recebeu a mensagem pela primeira vez.
