declare namespace API {
  type AIChatMessage = {
    content?: string;
    role?: string;
  };

  type AIChatRequest = {
    /** 消息列表 */
    messages?: AIChatMessage[];
    /** 模型名称 */
    model?: string;
    /** 是否流式返回 */
    stream?: boolean;
  };

  type ChatCreateRequest = {
    /** 模型配置ID */
    modelConfigId?: number;
    /** 对话标题 */
    title?: string;
  };

  type ChatVO = {
    /** 创建时间 */
    createdAt?: string;
    /** 对话ID */
    id?: number;
    /** 模型配置ID */
    modelConfigId?: number;
    /** 对话标题 */
    title?: string;
    /** 更新时间 */
    updatedAt?: string;
    /** 用户ID */
    userId?: number;
  };

  type createChatUsingPOSTParams = {
    /** userId */
    userId: number;
  };

  type deleteChatUsingDELETEParams = {
    /** chatId */
    chatId: number;
    /** userId */
    userId: number;
  };

  type deleteConfigUsingDELETEParams = {
    /** id */
    id: number;
  };

  type editUserInfoUsingPUTParams = {
    /** id */
    id: number;
  };

  type ExtraInfo = {
    modelConfigId?: number;
  };

  type getCurrentConfigUsingGETParams = {
    /** userId */
    userId: number;
  };

  type getUserInfoUsingGETParams = {
    /** id */
    id: number;
  };

  type listChatsUsingGETParams = {
    /** userId */
    userId: number;
  };

  type listConfigsUsingGETParams = {
    /** userId */
    userId: number;
  };

  type listMessagesUsingGETParams = {
    /** chatId */
    chatId: number;
  };

  type LLMConfigRequest = {
    apiKey?: string;
    apiUrl?: string;
    isEnabled?: boolean;
    modelId?: string;
    modelName?: string;
    userId?: number;
  };

  type LLMConfigVO = {
    /** API密钥 */
    apiKey?: string;
    /** API地址 */
    apiUrl?: string;
    /** 模型配置ID */
    id?: number;
    /** 是否启用 */
    isEnabled?: boolean;
    /** 模型ID */
    modelId?: string;
    /** 模型名称 */
    modelName?: string;
    /** 用户ID */
    userId?: number;
  };

  type MessageCreateRequest = {
    /** 对话ID */
    chatId?: number;
    /** 消息内容 */
    content?: string;
    /** 消息角色 */
    role?: string;
  };

  type MessageVO = {
    /** 对话ID */
    chatId?: number;
    /** 消息内容 */
    content?: string;
    /** 创建时间 */
    createdAt?: string;
    /** 主键ID */
    id?: number;
    /** 发送者类型 */
    senderType?: string;
  };

  type ResultBoolean_ = {
    code?: number;
    data?: boolean;
    message?: string;
    success?: boolean;
  };

  type ResultChatVO_ = {
    code?: number;
    data?: ChatVO;
    message?: string;
    success?: boolean;
  };

  type ResultListChatVO_ = {
    code?: number;
    data?: ChatVO[];
    message?: string;
    success?: boolean;
  };

  type ResultListLLMConfigVO_ = {
    code?: number;
    data?: LLMConfigVO[];
    message?: string;
    success?: boolean;
  };

  type ResultListMessageVO_ = {
    code?: number;
    data?: MessageVO[];
    message?: string;
    success?: boolean;
  };

  type ResultLLMConfigVO_ = {
    code?: number;
    data?: LLMConfigVO;
    message?: string;
    success?: boolean;
  };

  type ResultMessageVO_ = {
    code?: number;
    data?: MessageVO;
    message?: string;
    success?: boolean;
  };

  type ResultUserVO_ = {
    code?: number;
    data?: UserVO;
    message?: string;
    success?: boolean;
  };

  type saveMessageUsingPOSTParams = {
    /** userId */
    userId: number;
  };

  type sendMessageUsingPOSTParams = {
    /** userId */
    userId: number;
  };

  type SseEmitter = {
    timeout?: number;
  };

  type updateConfigUsingPUTParams = {
    /** id */
    id: number;
  };

  type updateStatusUsingPUTParams = {
    /** id */
    id: number;
  };

  type UserEditRequest = {
    avatar?: string;
    email?: string;
    modelConfigId?: number;
    newPassword?: string;
    nickname?: string;
    oldPassword?: string;
    username?: string;
  };

  type UserLoginRequest = {
    password?: string;
    username?: string;
  };

  type UserRegisterRequest = {
    confirmPassword?: string;
    email?: string;
    password?: string;
    username?: string;
  };

  type UserVO = {
    avatar?: string;
    email?: string;
    extraInfo?: ExtraInfo;
    id?: number;
    nickname?: string;
    username?: string;
  };
}
