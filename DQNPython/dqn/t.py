import torch

print(torch.cuda.is_available())  # True if GPU is available
print(torch.cuda.device_count())  # Number of available GPUs
print(torch.cuda.get_device_name(0))  # Name of the first GPU
